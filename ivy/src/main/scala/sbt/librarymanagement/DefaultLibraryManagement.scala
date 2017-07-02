package sbt
package librarymanagement

import java.io.File
import sbt.internal.librarymanagement._
import sbt.util.Logger
import sbt.io.Hash

class DefaultLibraryManagement(ivyConfiguration: IvyConfiguration,
                               updateOptons: UpdateOptions,
                               log: Logger)
    extends LibraryManagement {
  private[sbt] val ivySbt: IvySbt = new IvySbt(ivyConfiguration)
  private val sbtOrgTemp = JsonUtil.sbtOrgTemp
  private val modulePrefixTemp = "temp-module-"

  type Module = ivySbt.Module

  /**
   * Returns a dummy module that depends on `moduleID`.
   * Note: Sbt's implementation of Ivy requires us to do this, because only the dependencies
   *       of the specified module will be downloaded.
   */
  def buildModule(moduleId: ModuleID): Module = buildModule(moduleId, None)

  def buildModule(moduleId: ModuleID, scalaModuleInfo: Option[ScalaModuleInfo]): Module = {
    val sha1 = Hash.toHex(Hash(moduleId.name))
    val dummyID = ModuleID(sbtOrgTemp, modulePrefixTemp + sha1, moduleId.revision)
      .withConfigurations(moduleId.configurations)
    buildModule(dummyID, Vector(moduleId), scalaModuleInfo)
  }

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

  private def dependenciesNames(module: ivySbt.Module): String =
    module.moduleSettings match {
      // `module` is a dummy module, we will only fetch its dependencies.
      case ic: InlineConfiguration =>
        ic.dependencies map {
          case mID: ModuleID =>
            import mID._
            s"$organization % $name % $revision"
        } mkString ", "
      case _ =>
        s"unknown"
    }

  def update(module: Module,
             retrieveDirectory: File,
             predicate: File => Boolean): Option[Vector[File]] = {
    val retrieveConfiguration = RetrieveConfiguration()
      .withRetrieveDirectory(retrieveDirectory)
    val updateConfiguration = UpdateConfiguration()
      .withRetrieve(retrieveConfiguration)
      .withMissingOk(true)
    log.debug(s"Attempting to fetch ${dependenciesNames(module)}. This operation may fail.")
    IvyActions.updateEither(
      module,
      updateConfiguration,
      UnresolvedWarningConfiguration(),
      LogicalClock.unknown,
      None,
      log
    ) match {
      case Left(unresolvedWarning) =>
        log.debug(s"Couldn't retrieve module ${dependenciesNames(module)}.")
        None
      case Right(updateReport) =>
        val allFiles =
          for {
            conf <- updateReport.configurations
            m <- conf.modules
            (_, f) <- m.artifacts
          } yield f

        log.debug(s"Files retrieved for ${dependenciesNames(module)}:")
        log.debug(allFiles mkString ", ")
        allFiles filter predicate match {
          case Seq() => None
          case files => Some(files)
        }
    }
  }
}
