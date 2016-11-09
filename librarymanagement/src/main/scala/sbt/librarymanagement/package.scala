package sbt

package object librarymanagement extends ResolversSyntax {
  type ExclusionRule = InclExclRule
  type InclusionRule = InclExclRule

  implicit def sbtRichConfigurationReport(configurationReport: ConfigurationReport): RichConfigurationReport = new RichConfigurationReport(configurationReport)
  implicit def sbtRichModuleReport(moduleReport: ModuleReport): RichModuleReport = new RichModuleReport(moduleReport)

  implicit def sbtRichModuleIDCompanion(x: ModuleID.type): ModuleIDCompanion.type = ModuleIDCompanion
}
