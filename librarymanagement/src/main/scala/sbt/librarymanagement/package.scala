package sbt

package object librarymanagement extends ResolversSyntax {
  type ExclusionRule = InclExclRule
  type InclusionRule = InclExclRule

  implicit def sbtRichConfigurationReport(configurationReport: ConfigurationReport): RichConfigurationReport = new RichConfigurationReport(configurationReport)
  implicit def sbtRichModuleReport(moduleReport: ModuleReport): RichModuleReport = new RichModuleReport(moduleReport)

  implicit def sbtRichConflictManagerCompanion(x: ConflictManager.type): ConflictManagerCompanion.type = ConflictManagerCompanion
  implicit def sbtRichIvyScalaCompanion(x: IvyScala.type): IvyScalaCompanion.type = IvyScalaCompanion
  implicit def sbtRichModuleIDCompanion(x: ModuleID.type): ModuleIDCompanion.type = ModuleIDCompanion
}
