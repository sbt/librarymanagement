package sbt.librarymanagement.coursier

import java.io.File

import coursier.{ MavenRepository, Resolution, _ }
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
      toUpdateResult(resolution, localArtifacts)
    } else {
      toSbtError(uwconfig, resolution)
    }
  }

  // utilities

  private def toCoursierDependency(moduleID: ModuleID): Dependency =
    Dependency(Module(moduleID.organization, moduleID.name), moduleID.revision)

  private def toUpdateResult(
      resolution: Resolution,
      localArtificats: Seq[FileError \/ File]): Either[UnresolvedWarning, UpdateReport] = {

    val errors = localArtificats.collect {
      case -\/(fileError) => fileError
    }

    lazy val downloaded = localArtificats.collect {
      case \/-(file) => file
    }

    if (errors.isEmpty) {
      // FIXME: use correct times
      val updateStats = UpdateStats(0l, 0l, 0l, false)
      val cachedDescriptor: File = ???
      val configurations: Vector[ConfigurationReport] = ???
      val stamps: Map[File, Long] = Map.empty
      Right(UpdateReport(cachedDescriptor, configurations, updateStats, stamps))
    } else {
      throw new RuntimeException(s"Could not save downloaded dependencies: $errors")
    }

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
