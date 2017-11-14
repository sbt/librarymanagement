package sbt.librarymanagement.coursier

import java.io.File

import coursier.{ Resolution, _ }
import sbt.librarymanagement._
import sbt.util.Logger

import scalaz.\/
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
    ModuleSettings
    CoursierModuleDescriptor(
      moduleSetting.dependencies,
      moduleSetting.scalaModuleInfo,
      CoursierModuleSettings(),
      1L
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
    val start = Resolution(
      Set(
        Dependency(
          Module("org.typelevel", "cats-core_2.11"),
          "0.6.0"
        ),
        Dependency(
          Module("org.scalaz", "scalaz-core_2.11"),
          "7.2.3"
        )
      )
    )
    val fetch = Fetch.from(repositories, Cache.fetch())
    val resolution = start.process.run(fetch).unsafePerformSync
    val localArtifacts: Seq[FileError \/ File] = Task
      .gatherUnordered(
        resolution.artifacts.map(Cache.file(_).run)
      )
      .unsafePerformSync
    ???
  }
}
