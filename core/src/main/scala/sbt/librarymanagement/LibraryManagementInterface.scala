package sbt.librarymanagement

import java.io.File
import sbt.util.Logger

/**
 * Interface for library management
 */
abstract class LibraryManagementInterface {

  /**
   * Builds a ModuleDescriptor that describes a subproject with dependencies.
   *
   * @param moduleSetting It contains the information about the module including the dependencies.
   */
  def buildModule(moduleSetting: InlineConfiguration): ModuleDescriptor

  /**
   * Resolves the given module's dependencies performing a retrieval.
   *
   * @param module The module to be resolved.
   * @param configuration The update configuration.
   * @param uwconfig The configuration to handle unresolved warnings.
   * @param log The logger.
   * @return The result, either an unresolved warning or an update report. Note that this
   *         update report will or will not be successful depending on the `missingOk` option.
   */
  def updateEither(module: ModuleDescriptor,
                   configuration: UpdateConfiguration,
                   uwconfig: UnresolvedWarningConfiguration,
                   log: Logger): Either[UnresolvedWarning, UpdateReport]

  /**
   * Publishes the given module.
   *
   * @param module The module to be resolved.
   * @param configuration The publish configuration.
   * @param log The logger.
   */
  def publish(module: ModuleDescriptor, configuration: PublishConfiguration, log: Logger): Unit

  /**
   * Makes the .ivy file for the given module.
   *
   * @param module The module to be resolved.
   * @param configuration The makeIvyFile configuration.
   * @param log The logger.
   */
  def makeIvyFile(module: ModuleDescriptor, configuration: DeliverConfiguration, log: Logger): File

  /**
   * Makes the .pom file for the given module.
   *
   * @param module The module to be resolved.
   * @param configuration The makeIvyFile configuration.
   * @param log The logger.
   */
  def makePomFile(module: ModuleDescriptor, configuration: MakePomConfiguration, log: Logger): File
}

object LibraryManagementInterface

trait ModuleDescriptor {
  def directDependenciesForWarning: Vector[ModuleID]
  def scalaModuleInfo: Option[ScalaModuleInfo]
}
