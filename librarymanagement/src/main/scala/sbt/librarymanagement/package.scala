package sbt

package object librarymanagement extends ResolversSyntax {
  type ExclusionRule = InclExclRule
  type InclusionRule = InclExclRule

  implicit def sbtRichModuleReport(moduleReport: ModuleReport): RichModuleReport = new RichModuleReport(moduleReport)
}
