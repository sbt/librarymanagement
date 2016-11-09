/* sbt -- Simple Build Tool
 * Copyright 2008, 2009, 2010  Mark Harrah
 */
package sbt.librarymanagement

import org.apache.ivy.core.module.descriptor
import org.apache.ivy.util.filter.{ Filter => IvyFilter }

abstract class InclExclRuleCompanion {
  def everything = new InclExclRule("*", "*", "*", Vector.empty)
}

abstract class ArtifactTypeFilterParent {
  def types: Set[String]
  def inverted: Boolean

  def copy(types: Set[String] = types, inverted: Boolean = inverted): ArtifactTypeFilter

  def invert = copy(inverted = !inverted)
  def apply(a: descriptor.Artifact): Boolean = (types contains a.getType) ^ inverted
}

abstract class ArtifactTypeFilterCompanion {
  def allow(types: Set[String]) = ArtifactTypeFilter(types, false)
  def forbid(types: Set[String]) = ArtifactTypeFilter(types, true)

  implicit def toIvyFilter(f: ArtifactTypeFilter): IvyFilter = new IvyFilter {
    override def accept(o: Object): Boolean = Option(o) exists { case a: descriptor.Artifact => f.apply(a) }
  }
}

/** See http://ant.apache.org/ivy/history/latest-milestone/settings/conflict-managers.html for details of the different conflict managers.*/
object ConflictManagerCompanion {
  val all = ConflictManager("all")
  val latestTime = ConflictManager("latest-time")
  val latestRevision = ConflictManager("latest-revision")
  val latestCompatible = ConflictManager("latest-compatible")
  val strict = ConflictManager("strict")
  val default = latestRevision
}
