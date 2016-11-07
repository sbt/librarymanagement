/* sbt -- Simple Build Tool
 * Copyright 2008, 2009, 2010  Mark Harrah
 */
package sbt.internal.librarymanagement

import java.io.File

import scala.xml.NodeSeq

import sbt.librarymanagement._
import xsbti._

object InlineIvyConfigurationCompanion {
  def apply(
    paths: IvyPaths,
    resolvers: Vector[Resolver],
    otherResolvers: Vector[Resolver],
    moduleConfigurations: Vector[ModuleConfiguration],
    localOnly: Boolean,
    lock: Option[GlobalLock],
    checksums: Vector[String],
    resolutionCacheDir: Option[File],
    updateOptions: UpdateOptions,
    log: Logger
  ): InlineIvyConfiguration =
    new InlineIvyConfiguration(lock, paths.baseDirectory, log, updateOptions, paths, resolvers, otherResolvers,
      moduleConfigurations, localOnly, checksums, resolutionCacheDir)
}

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
}
