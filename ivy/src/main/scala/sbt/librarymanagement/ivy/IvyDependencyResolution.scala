package sbt
package librarymanagement
package ivy

import sbt.internal.librarymanagement._
import sbt.util.Logger

class IvyDependencyResolution private[sbt] (val ivySbt: IvySbt)
    extends DependencyResolutionInterface {
  type Module = ivySbt.Module

  override def moduleDescriptor(moduleSetting: ModuleDescriptorConfiguration): ModuleDescriptor = {
    new Module(moduleSetting)
  }

  override def update(
      module: ModuleDescriptor,
      configuration: UpdateConfiguration,
      uwconfig: UnresolvedWarningConfiguration,
      log: Logger
  ): Either[UnresolvedWarning, UpdateReport] =
    IvyActions.updateEither(toModule(module), configuration, uwconfig, log)

  override def withAddedResolvers(
      resolvers: Seq[Resolver],
      log: Logger
  ): IvyDependencyResolution = {
    val newConfiguration = ivySbt.configuration match {
      case configuration: ExternalIvyConfiguration =>
        configuration.withExtraResolvers(configuration.extraResolvers ++ resolvers)
      case configuration: InlineIvyConfiguration =>
        configuration.withResolvers(configuration.resolvers ++ resolvers)
    }
    new IvyDependencyResolution(new IvySbt(newConfiguration))
  }

  private[sbt] def toModule(module: ModuleDescriptor): Module =
    module match {
      case m: Module @unchecked => m
    }
}

object IvyDependencyResolution {
  def apply(ivyConfiguration: IvyConfiguration): DependencyResolution =
    DependencyResolution(new IvyDependencyResolution(new IvySbt(ivyConfiguration)))
}
