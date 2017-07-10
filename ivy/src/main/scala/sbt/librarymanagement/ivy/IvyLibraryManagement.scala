package sbt
package librarymanagement
package ivy

import sbt.internal.librarymanagement._
import sbt.util.Logger
import java.io.File

class IvyLibraryManagement(ivyConfiguration: IvyConfiguration) extends LibraryManagement {
  private[sbt] val ivySbt: IvySbt = new IvySbt(ivyConfiguration)

  type Module = ivySbt.Module

  override def moduleDescriptor(moduleSetting: InlineConfiguration): ModuleDescriptor = {
    new Module(moduleSetting)
  }

  override def update(module: ModuleDescriptor,
                      configuration: UpdateConfiguration,
                      uwconfig: UnresolvedWarningConfiguration,
                      log: Logger): Either[UnresolvedWarning, UpdateReport] =
    IvyActions.updateEither(toModule(module), configuration, uwconfig, log)

  override def makeIvyFile(module: ModuleDescriptor,
                           configuration: DeliverConfiguration,
                           log: Logger): File =
    IvyActions.deliver(toModule(module), configuration, log)

  override def makePomFile(module: ModuleDescriptor,
                           configuration: MakePomConfiguration,
                           log: Logger): File =
    IvyActions.makePomFile(toModule(module), configuration, log)

  override def publish(module: ModuleDescriptor,
                       configuration: PublishConfiguration,
                       log: Logger): Unit =
    IvyActions.publish(toModule(module), configuration, log)

  private[sbt] def toModule(module: ModuleDescriptor): Module =
    module match {
      case m: Module => m
    }
}
