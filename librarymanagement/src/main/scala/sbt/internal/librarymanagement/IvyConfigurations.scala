/* sbt -- Simple Build Tool
 * Copyright 2008, 2009, 2010  Mark Harrah
 */
package sbt.internal.librarymanagement

import scala.xml.NodeSeq

import sbt.librarymanagement._

object InlineConfigurationCompanion {
  def apply(
    module: ModuleID,
    moduleInfo: ModuleInfo,
    dependencies: Vector[ModuleID],
    overrides: Set[ModuleID] = Set.empty,
    excludes: Vector[SbtExclusionRule] = Vector.empty,
    ivyXML: NodeSeq = NodeSeq.Empty,
    configurations: Vector[Configuration] = Vector.empty,
    defaultConfiguration: Option[Configuration] = None,
    ivyScala: Option[IvyScala] = None,
    validate: Boolean = false,
    conflictManager: ConflictManager = ConflictManager.default
  ): InlineConfiguration =
    new InlineConfiguration(validate, ivyScala, module, moduleInfo, dependencies, overrides, excludes, ivyXML,
      configurations, defaultConfiguration, conflictManager)

  def configurations(explicitConfigurations: Iterable[Configuration], defaultConfiguration: Option[Configuration]) =
    if (explicitConfigurations.isEmpty) {
      defaultConfiguration match {
        case Some(Configurations.DefaultIvyConfiguration) => Configurations.Default :: Nil
        case Some(Configurations.DefaultMavenConfiguration) => Configurations.defaultMavenConfigurations
        case _ => Nil
      }
    } else
      explicitConfigurations
}
