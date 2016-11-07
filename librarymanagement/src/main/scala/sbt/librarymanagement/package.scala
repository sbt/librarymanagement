package sbt

package object librarymanagement extends ResolversSyntax {
  type ExclusionRule = InclExclRule
  type InclusionRule = InclExclRule

  implicit def sbtRichArtifact(artifact: Artifact): RichArtifact = new RichArtifact(artifact)
  implicit def sbtRichArtifactFilterType(artifactTypeFilter: ArtifactTypeFilter): RichArtifactTypeFilter = new RichArtifactTypeFilter(artifactTypeFilter)
  implicit def sbtRichConfiguration(configuration: Configuration): RichConfiguration = new RichConfiguration(configuration)
  implicit def sbtRichConfigurationReport(configurationReport: ConfigurationReport): RichConfigurationReport = new RichConfigurationReport(configurationReport)
  implicit def sbtRichModuleID(moduleID: ModuleID): RichModuleID = new RichModuleID(moduleID)
  implicit def sbtRichModuleReport(moduleReport: ModuleReport): RichModuleReport = new RichModuleReport(moduleReport)

  implicit def sbtRichArtifactCompanion(x: Artifact.type): ArtifactCompanion.type = ArtifactCompanion
  implicit def sbtRichConflictManagerCompanion(x: ConflictManager.type): ConflictManagerCompanion.type = ConflictManagerCompanion
  implicit def sbtRichCrossVersionCompanion(x: CrossVersion.type): CrossVersionCompanion.type = CrossVersionCompanion
  implicit def sbtRichIvyScalaCompanion(x: IvyScala.type): IvyScalaCompanion.type = IvyScalaCompanion
  implicit def sbtRichModuleIDCompanion(x: ModuleID.type): ModuleIDCompanion.type = ModuleIDCompanion
  implicit def sbtRichResolverCompanion(x: Resolver.type): ResolverCompanion.type = ResolverCompanion
}
