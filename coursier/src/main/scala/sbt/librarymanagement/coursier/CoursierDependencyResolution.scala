package sbt.librarymanagement.coursier

import java.io.{ File, OutputStreamWriter }

import coursier.{ Artifact, MavenRepository, Resolution, _ }
import sbt.librarymanagement.Configurations.{ CompilerPlugin, Component, ScalaTool }
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

  /**
   * Builds a ModuleDescriptor that describes a subproject with dependencies.
   *
   * @param moduleSetting It contains the information about the module including the dependencies.
   * @return A `ModuleDescriptor` describing a subproject and its dependencies.
   */
  override def moduleDescriptor(
      moduleSetting: ModuleDescriptorConfiguration): CoursierModuleDescriptor = {
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
      val localArtifacts: Seq[(Artifact, FileError \/ File)] = Task
        .gatherUnordered(
          resolution.artifacts.map(a =>
            Cache.file(artifact = a, logger = Some(createLogger())).run.map(t => (a, t)))
        )
        .unsafePerformSync
      toUpdateReport(resolution, localArtifacts, log)
    } else {
      toSbtError(uwconfig, resolution)
    }
  }

  // utilities

  private def createLogger() = {
    val t = new TermDisplay(new OutputStreamWriter(System.err))
    t.init()
    t
  }

  private def toCoursierDependency(moduleID: ModuleID): Dependency = {
    val attrs = moduleID.extraAttributes
      .map(kv => Attributes(kv._1, kv._2))
      .headOption
      .getOrElse(Attributes())
    Dependency(Module(moduleID.organization, moduleID.name),
               moduleID.revision,
               moduleID.configurations.getOrElse(""),
               attrs)
  }

  private def toUpdateReport(resolution: Resolution,
                             localArtificats: Seq[(Artifact, FileError \/ File)],
                             log: Logger): Either[UnresolvedWarning, UpdateReport] = {

    // can be non empty only if ignoreArtifactErrors is true or some optional artifacts are not found
    val erroredArtifacts = localArtificats.collect {
      case (artifact, -\/(_)) => artifact
    }.toSet

    lazy val downloaded = localArtificats.collect {
      case (artifact, \/-(file)) => (artifact, file)
    }

    val depsByConfig = resolution.dependencies.groupBy(_.configuration).mapValues(_.toSeq)

    val configurations = extractConfigurationTree

    val configResolutions =
      (depsByConfig.keys ++ configurations.keys).map(k => (k, resolution)).toMap

    val sbtBootJarOverrides = Map.empty[(Module, String), File] // TODO: get correct values
    val classifiers = None // TODO: get correct values
    val artifactFiles = downloaded.toMap

    if (erroredArtifacts.isEmpty) {
      Right(
        ToSbt.updateReport(
          depsByConfig,
          configResolutions,
          configurations,
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
          log
        ))
    } else {
      throw new RuntimeException(s"Could not save downloaded dependencies: $erroredArtifacts")
    }

  }

  type ConfigurationName = String
  type ConfigurationDependencyTree = Map[ConfigurationName, Set[ConfigurationName]]

  // Key is the name of the configuration (i.e. `compile`) and the values are the name itself plus the
  // names of the configurations that this one depends on.
  private def extractConfigurationTree: ConfigurationDependencyTree = {
    (Configurations.default ++ Configurations.defaultInternal ++ Seq(ScalaTool,
                                                                     CompilerPlugin,
                                                                     Component))
      .map(c => (c.name, c.extendsConfigs.map(_.name) :+ c.name))
      .toMap
      .mapValues(_.toSet)
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

object CoursierDependencyResolution {
  def apply() = DependencyResolution(new CoursierDependencyResolution())
}
