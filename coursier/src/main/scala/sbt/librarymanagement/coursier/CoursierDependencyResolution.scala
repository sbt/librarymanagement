package sbt.librarymanagement.coursier

import java.io.File

import coursier.{ MavenRepository, Resolution, Artifact, _ }
import sbt.librarymanagement._
import sbt.util.Logger

import scalaz.{ -\/, \/, \/- }
import scalaz.concurrent.Task

case class CoursierModuleDescriptor(
    directDependencies: Vector[ModuleID],
    scalaModuleInfo: Option[ScalaModuleInfo],
    moduleSettings: ModuleSettings,
    extraInputHash: Long
) extends ModuleDescriptor

case class CoursierModuleSettings() extends ModuleSettings

class CoursierDependencyResolution private[sbt] extends DependencyResolutionInterface {
  val repositories = Seq(
    Cache.ivy2Local,
    MavenRepository("https://repo1.maven.org/maven2")
  )

  type Module = CoursierModuleDescriptor

  /**
   * Builds a ModuleDescriptor that describes a subproject with dependencies.
   *
   * @param moduleSetting It contains the information about the module including the dependencies.
   * @return A `ModuleDescriptor` describing a subproject and its dependencies.
   */
  override def moduleDescriptor(moduleSetting: ModuleDescriptorConfiguration): Module = {
    CoursierModuleDescriptor(
      moduleSetting.dependencies,
      moduleSetting.scalaModuleInfo,
      CoursierModuleSettings(),
      1L // FIXME: use correct value
    )
  }

  /**
   * Resolves the given module's dependencies performing a retrieval.
   *
   * @param module        The module to be resolved.
   * @param configuration The update configuration.
   * @param uwconfig      The configuration to handle unresolved warnings.
   * @param log           The logger.
   * @return The result, either an unresolved warning or an update report. Note that this
   *         update report will or will not be successful depending on the `missingOk` option.
   */
  override def update(module: ModuleDescriptor,
                      configuration: UpdateConfiguration,
                      uwconfig: UnresolvedWarningConfiguration,
                      log: Logger): Either[UnresolvedWarning, UpdateReport] = {
    val dependencies = module.directDependencies.map(toCoursierDependency).toSet
    val start = Resolution(dependencies)
    val fetch = Fetch.from(repositories, Cache.fetch())
    val resolution = start.process.run(fetch).unsafePerformSync

    if (resolution.metadataErrors.isEmpty) {
      val localArtifacts: Seq[FileError \/ File] = Task
        .gatherUnordered(
          resolution.artifacts.map(Cache.file(_).run)
        )
        .unsafePerformSync
      toUpdateResult(resolution, localArtifacts, log)
    } else {
      toSbtError(uwconfig, resolution)
    }
  }

  // utilities

  private def toCoursierDependency(moduleID: ModuleID): Dependency =
    Dependency(Module(moduleID.organization, moduleID.name), moduleID.revision)

  private def toUpdateResult(resolution: Resolution,
                             localArtificats: Seq[FileError \/ File],
                             log: Logger): Either[UnresolvedWarning, UpdateReport] = {

    // can be non empty only if ignoreArtifactErrors is true or some optional artifacts are not found
    val erroredArtifacts = localArtificats.collect {
      case (artifact, -\/(_)) => artifact
    }.toSet

    lazy val downloaded = localArtificats.collect {
      case \/-(file) => file
    }

    val depsByConfig = resolution.dependencies.groupBy(_.configuration).mapValues(_.toSeq)
    val configResolutions = depsByConfig.mapValues(_ => resolution)

    if (erroredArtifacts.isEmpty) {
      ToSbt.updateReport(
        depsByConfig,
        configResolutions,
        configs,
        classifiers,
        artifactFileOpt(
          sbtBootJarOverrides,
          artifactFiles,
          erroredArtifacts,
          log,
          _,
          _,
          _
        ),
        log,
        includeSignatures = includeSignatures
      )
    } else {
      throw new RuntimeException(s"Could not save downloaded dependencies: $erroredArtifacts")
    }

  }

  private def artifactFileOpt(
      sbtBootJarOverrides: Map[(Module, String), File],
      artifactFiles: Map[Artifact, File],
      erroredArtifacts: Set[Artifact],
      log: Logger,
      module: Module,
      version: String,
      artifact: Artifact
  ) = {

    val artifact0 = artifact
      .copy(attributes = Attributes()) // temporary hack :-(

    // Under some conditions, SBT puts the scala JARs of its own classpath
    // in the application classpath. Ensuring we return SBT's jars rather than
    // JARs from the coursier cache, so that a same JAR doesn't land twice in the
    // application classpath (once via SBT jars, once via coursier cache).
    val fromBootJars =
      if (artifact.classifier.isEmpty && artifact.`type` == "jar")
        sbtBootJarOverrides.get((module, version))
      else
        None

    val res = fromBootJars.orElse(artifactFiles.get(artifact0))

    if (res.isEmpty && !erroredArtifacts(artifact0))
      log.error(s"${artifact.url} not downloaded (should not happen)")

    res
  }

  private def toSbtError(uwconfig: UnresolvedWarningConfiguration, resolution: Resolution) = {
    val failedResolution = resolution.metadataErrors.map {
      case ((failedModule, failedVersion), _) =>
        ModuleID(failedModule.organization, failedModule.name, failedVersion)
    }
    val msgs = resolution.metadataErrors.flatMap(_._2)
    val ex = new ResolveException(msgs, failedResolution)
    Left(UnresolvedWarning(ex, uwconfig))
  }
}
