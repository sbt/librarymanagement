/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class PublishConfiguration private (
  val metadataFile: Option[java.io.File],
  val resolverName: Option[String],
  val artifacts: Vector[scala.Tuple2[sbt.librarymanagement.Artifact, java.io.File]],
  val checksums: scala.Vector[String],
  val logging: Option[sbt.librarymanagement.UpdateLogging],
  val overwrite: Boolean) extends Serializable {
  
  private def this() = this(None, None, Vector(), Vector("sha1", "md5"), None, false)
  
  override def equals(o: Any): Boolean = o match {
    case x: PublishConfiguration => (this.metadataFile == x.metadataFile) && (this.resolverName == x.resolverName) && (this.artifacts == x.artifacts) && (this.checksums == x.checksums) && (this.logging == x.logging) && (this.overwrite == x.overwrite)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "PublishConfiguration".##) + metadataFile.##) + resolverName.##) + artifacts.##) + checksums.##) + logging.##) + overwrite.##)
  }
  override def toString: String = {
    "PublishConfiguration(" + metadataFile + ", " + resolverName + ", " + artifacts + ", " + checksums + ", " + logging + ", " + overwrite + ")"
  }
  protected[this] def copy(metadataFile: Option[java.io.File] = metadataFile, resolverName: Option[String] = resolverName, artifacts: Vector[scala.Tuple2[sbt.librarymanagement.Artifact, java.io.File]] = artifacts, checksums: scala.Vector[String] = checksums, logging: Option[sbt.librarymanagement.UpdateLogging] = logging, overwrite: Boolean = overwrite): PublishConfiguration = {
    new PublishConfiguration(metadataFile, resolverName, artifacts, checksums, logging, overwrite)
  }
  def withMetadataFile(metadataFile: Option[java.io.File]): PublishConfiguration = {
    copy(metadataFile = metadataFile)
  }
  def withMetadataFile(metadataFile: java.io.File): PublishConfiguration = {
    copy(metadataFile = Option(metadataFile))
  }
  def withResolverName(resolverName: Option[String]): PublishConfiguration = {
    copy(resolverName = resolverName)
  }
  def withResolverName(resolverName: String): PublishConfiguration = {
    copy(resolverName = Option(resolverName))
  }
  def withArtifacts(artifacts: Vector[scala.Tuple2[sbt.librarymanagement.Artifact, java.io.File]]): PublishConfiguration = {
    copy(artifacts = artifacts)
  }
  def withChecksums(checksums: scala.Vector[String]): PublishConfiguration = {
    copy(checksums = checksums)
  }
  def withLogging(logging: Option[sbt.librarymanagement.UpdateLogging]): PublishConfiguration = {
    copy(logging = logging)
  }
  def withLogging(logging: sbt.librarymanagement.UpdateLogging): PublishConfiguration = {
    copy(logging = Option(logging))
  }
  def withOverwrite(overwrite: Boolean): PublishConfiguration = {
    copy(overwrite = overwrite)
  }
}
object PublishConfiguration {
  
  def apply(): PublishConfiguration = new PublishConfiguration(None, None, Vector(), Vector("sha1", "md5"), None, false)
  def apply(metadataFile: Option[java.io.File], resolverName: Option[String], artifacts: Vector[scala.Tuple2[sbt.librarymanagement.Artifact, java.io.File]], checksums: scala.Vector[String], logging: Option[sbt.librarymanagement.UpdateLogging], overwrite: Boolean): PublishConfiguration = new PublishConfiguration(metadataFile, resolverName, artifacts, checksums, logging, overwrite)
  def apply(metadataFile: java.io.File, resolverName: String, artifacts: Vector[scala.Tuple2[sbt.librarymanagement.Artifact, java.io.File]], checksums: scala.Vector[String], logging: sbt.librarymanagement.UpdateLogging, overwrite: Boolean): PublishConfiguration = new PublishConfiguration(Option(metadataFile), Option(resolverName), artifacts, checksums, Option(logging), overwrite)
}
