package sbt
package librarymanagement

import java.io.File
import sbt.internal.librarymanagement._
import sbt.util.Logger
import sbt.io.Hash

class DefaultLibraryManagement(ivyConfiguration: IvyConfiguration,
                               updateOptons: UpdateOptions,
                               log: Logger)
    extends AbstractLibraryManagement {
  private[sbt] val ivySbt: IvySbt = new IvySbt(ivyConfiguration)
  private val sbtOrgTemp = JsonUtil.sbtOrgTemp
  private val modulePrefixTemp = "temp-module-"

  type Module = ivySbt.Module

  /**
   * Build a ModuleDescriptor that describes a subproject with dependencies.
   */
  def buildModule(
      moduleId: ModuleID,
      deps: Vector[ModuleID],
      scalaModuleInfo: Option[ScalaModuleInfo]
  ): Module = {
    val moduleSetting = InlineConfiguration(
      validate = false,
      scalaModuleInfo = scalaModuleInfo,
      module = moduleId,
      moduleInfo = ModuleInfo(moduleId.name),
      dependencies = deps
    ).withConfigurations(Vector(Configurations.Component))
    new Module(moduleSetting)
  }

  /**
   * Updates one module's dependencies performing a dependency resolution and retrieval.
   *
   * @param module The module to be resolved.
   * @param configuration The update configuration.
   * @param uwconfig The configuration to handle unresolved warnings.
   * @param logicalClock The clock necessary to cache ivy.
   * @param metadataDirectory The base directory used for caching resolution.
   * @param log The logger.
   * @return The result, either an unresolved warning or an update report. Note that this
   *         update report will or will not be successful depending on the `missingOk` option.
   */
  def updateEither(module: ModuleDescriptor,
                   configuration: UpdateConfiguration,
                   uwconfig: UnresolvedWarningConfiguration,
                   logicalClock: LogicalClock,
                   metadataDirectory: Option[File],
                   log: Logger): Either[UnresolvedWarning, UpdateReport] =
    IvyActions.updateEither(toModule(module),
                            configuration,
                            uwconfig,
                            logicalClock,
                            metadataDirectory,
                            log)

  private[sbt] def toModule(module: ModuleDescriptor): Module =
    module match {
      case m: Module => m
    }
}
