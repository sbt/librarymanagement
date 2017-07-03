package sbt.librarymanagement

import java.io.File
import sbt.util.Logger
import sbt.io.Hash

/**
 * Interface for library management
 */
abstract class LibraryManagement {

  /**
   * Build a ModuleDescriptor that describes a subproject with dependencies.
   */
  def buildModule(moduleId: ModuleID,
                  directDependencies: Vector[ModuleID],
                  scalaModuleInfo: Option[ScalaModuleInfo]): ModuleDescriptor

  /**
   * Resolves the given module's dependencies performing a retrieval.
   *
   * @param module The module to be resolved.
   * @param configuration The update configuration.
   * @param uwconfig The configuration to handle unresolved warnings.
   * @param metadataDirectory The base directory used for caching resolution.
   * @param log The logger.
   * @return The result, either an unresolved warning or an update report. Note that this
   *         update report will or will not be successful depending on the `missingOk` option.
   */
  def updateEither(module: ModuleDescriptor,
                   configuration: UpdateConfiguration,
                   uwconfig: UnresolvedWarningConfiguration,
                   metadataDirectory: Option[File],
                   log: Logger): Either[UnresolvedWarning, UpdateReport]

  /**
   * Publishes the given module.
   *
   * @param module The module to be resolved.
   * @param configuration The publish configuration.
   * @param log The logger.
   */
  def publish(module: ModuleDescriptor, configuration: PublishConfiguration, log: Logger): Unit
}

/**
 * Helper mixin to provide methods for library management
 */
abstract class AbstractLibraryManagement extends LibraryManagement {
  import sbt.internal.librarymanagement.InternalDefaults._

  /**
   * Resolves the given dependency, and retrieves the artifacts to a directory.
   *
   * @param dependencyId The dependency to be resolved.
   * @param scalaModuleInfo The module info about Scala.
   * @param retrieveDirectory The directory to retrieve the files.
   * @param log The logger.
   * @return The result, either an unresolved warning or a sequence of files.
   */
  def retrieveEither(dependencyId: ModuleID,
                     scalaModuleInfo: Option[ScalaModuleInfo],
                     retrieveDirectory: File,
                     log: Logger): Either[UnresolvedWarning, Vector[File]] =
    retrieveEither(dummyModule(dependencyId, scalaModuleInfo), retrieveDirectory, log)

  /**
   * Resolves the given module's dependencies, and retrieves the artifacts to a directory.
   *
   * @param module The module to be resolved.
   * @param retrieveDirectory The directory to retrieve the files.
   * @param log The logger.
   * @return The result, either an unresolved warning or a sequence of files.
   */
  def retrieveEither(module: ModuleDescriptor,
                     retrieveDirectory: File,
                     log: Logger): Either[UnresolvedWarning, Vector[File]] = {
    // Using the default artifact type filter here, so sources and docs are excluded.
    val retrieveConfiguration = RetrieveConfiguration()
      .withRetrieveDirectory(retrieveDirectory)
    val updateConfiguration = UpdateConfiguration()
      .withRetrieveManaged(retrieveConfiguration)
    // .withMissingOk(true)
    log.debug(s"Attempting to fetch ${dependenciesNames(module)}. This operation may fail.")
    updateEither(
      module,
      updateConfiguration,
      UnresolvedWarningConfiguration(),
      None,
      log
    ) match {
      case Left(unresolvedWarning) => Left(unresolvedWarning)
      case Right(updateReport) =>
        val allFiles =
          for {
            conf <- updateReport.configurations
            m <- conf.modules
            (_, f) <- m.artifacts
          } yield f
        log.debug(s"Files retrieved for ${dependenciesNames(module)}:")
        log.debug(allFiles mkString ", ")
        // allFiles filter predicate match {
        //   case Seq() => None
        //   case files => Some(files)
        // }
        Right(allFiles)
    }
  }

  protected def dependenciesNames(module: ModuleDescriptor): String =
    (module.directDependenciesForWarning map {
      case mID: ModuleID =>
        import mID._
        s"$organization % $name % $revision"
    }).mkString(", ")

  /**
   * Returns a dummy module that depends on `dependencyId`.
   */
  def dummyModule(dependencyId: ModuleID): ModuleDescriptor =
    dummyModule(dependencyId, None)

  /**
   * Returns a dummy module that depends on `dependencyId`.
   */
  def dummyModule(dependencyId: ModuleID,
                  scalaModuleInfo: Option[ScalaModuleInfo]): ModuleDescriptor = {
    val sha1 = Hash.toHex(Hash(dependencyId.name))
    val dummyID = ModuleID(sbtOrgTemp, modulePrefixTemp + sha1, dependencyId.revision)
      .withConfigurations(dependencyId.configurations)
    buildModule(dummyID, Vector(dependencyId), scalaModuleInfo)
  }
}

object LibraryManagement

trait ModuleDescriptor {
  def directDependenciesForWarning: Vector[ModuleID]
  def scalaModuleInfo: Option[ScalaModuleInfo]
}
