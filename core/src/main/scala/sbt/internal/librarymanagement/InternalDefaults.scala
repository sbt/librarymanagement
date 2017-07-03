package sbt
package internal.librarymanagement

import java.io.File
import sbt.librarymanagement._
import sbt.io.syntax._

/**
 * This is a list of functions with default values.
 */
object InternalDefaults {
  val sbtOrgTemp = JsonUtil.sbtOrgTemp
  val modulePrefixTemp = "temp-module-"

  def getArtifactTypeFilter(opt: Option[ArtifactTypeFilter]): ArtifactTypeFilter =
    opt.getOrElse(Artifact.defaultArtifactTypeFilter)

  def getOffline(opt: Option[Boolean]): Boolean =
    opt.getOrElse(false)

  def getFrozen(opt: Option[Boolean]): Boolean =
    opt.getOrElse(false)

  def getMissingOk(opt: Option[Boolean]): Boolean =
    opt.getOrElse(false)

  def getUpdateLogging(opt: Option[UpdateLogging]): UpdateLogging =
    opt.getOrElse(UpdateLogging.Default)

  def getSync(opt: Option[Boolean]): Boolean =
    opt.getOrElse(false)

  def getPublishOverwrite(opt: Option[Boolean]): Boolean =
    opt.getOrElse(false)

  def defaultRetrieveDirectory: File =
    (new File(".")).getAbsoluteFile / "lib_managed"

  def getRetrieveDirectory(opt: Option[File]): File =
    opt.getOrElse(defaultRetrieveDirectory)

  def getRetrievePattern(opt: Option[String]): String =
    opt.getOrElse(Resolver.defaultRetrievePattern)

  def getChecksums(opt: Option[Vector[String]]): Vector[String] =
    opt.getOrElse(Vector("sha1", "md1"))

  def getLogicalClock(opt: Option[LogicalClock]): LogicalClock =
    opt.getOrElse(LogicalClock.unknown)
}
