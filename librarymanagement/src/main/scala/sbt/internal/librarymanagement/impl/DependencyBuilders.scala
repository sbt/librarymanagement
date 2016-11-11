/* sbt -- Simple Build Tool
 * Copyright 2009,2010  Mark Harrah
 */
package sbt.internal.librarymanagement
package impl

import StringUtilities.nonEmpty
import sbt.librarymanagement._

trait DependencyBuilders {
  final implicit def toGroupID(groupID: String): GroupID =
    {
      nonEmpty(groupID, "Group ID")
      new GroupID(groupID)
    }
  final implicit def toRepositoryName(name: String): RepositoryName =
    {
      nonEmpty(name, "Repository name")
      new RepositoryName(name)
    }
  final implicit def moduleIDConfigurable(m: ModuleID): ModuleIDConfigurable =
    {
      require(m.configurations.isEmpty, "Configurations already specified for module " + m)
      new ModuleIDConfigurable(m)
    }
}

final class GroupID private[sbt] (private[sbt] val groupID: String) {
  def %(artifactID: String) = groupArtifact(artifactID, Disabled())
  def %%(artifactID: String): GroupArtifactID = groupArtifact(artifactID, CrossVersion.binary)

  private def groupArtifact(artifactID: String, cross: CrossVersion) =
    {
      nonEmpty(artifactID, "Artifact ID")
      new GroupArtifactID(groupID, artifactID, cross)
    }
}
final class GroupArtifactID private[sbt] (
  private[sbt] val groupID: String,
  private[sbt] val artifactID: String,
  private[sbt] val crossVersion: CrossVersion
) {
  def %(revision: String): ModuleID =
    {
      nonEmpty(revision, "Revision")
      ModuleID(groupID, artifactID, revision).cross(crossVersion)
    }
}
final class ModuleIDConfigurable private[sbt] (moduleID: ModuleID) {
  def %(configuration: Configuration): ModuleID = %(configuration.name)

  def %(configurations: String): ModuleID =
    {
      nonEmpty(configurations, "Configurations")
      val c = configurations
      moduleID.copy(configurations = Some(c))
    }
}
final class RepositoryName private[sbt] (name: String) {
  def at(location: String) =
    {
      nonEmpty(location, "Repository location")
      new MavenRepository(name, location)
    }
}
