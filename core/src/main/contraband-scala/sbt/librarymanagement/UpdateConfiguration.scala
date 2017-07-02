/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class UpdateConfiguration private (
  val retrieve: Option[sbt.internal.librarymanagement.RetrieveConfiguration],
  val missingOk: Option[Boolean],
  val logging: Option[sbt.librarymanagement.UpdateLogging],
  val artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter],
  val offline: Option[Boolean],
  val frozen: Option[Boolean]) extends Serializable {
  
  private def this() = this(None, None, None, None, None, None)
  
  override def equals(o: Any): Boolean = o match {
    case x: UpdateConfiguration => (this.retrieve == x.retrieve) && (this.missingOk == x.missingOk) && (this.logging == x.logging) && (this.artifactFilter == x.artifactFilter) && (this.offline == x.offline) && (this.frozen == x.frozen)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "UpdateConfiguration".##) + retrieve.##) + missingOk.##) + logging.##) + artifactFilter.##) + offline.##) + frozen.##)
  }
  override def toString: String = {
    "UpdateConfiguration(" + retrieve + ", " + missingOk + ", " + logging + ", " + artifactFilter + ", " + offline + ", " + frozen + ")"
  }
  protected[this] def copy(retrieve: Option[sbt.internal.librarymanagement.RetrieveConfiguration] = retrieve, missingOk: Option[Boolean] = missingOk, logging: Option[sbt.librarymanagement.UpdateLogging] = logging, artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter] = artifactFilter, offline: Option[Boolean] = offline, frozen: Option[Boolean] = frozen): UpdateConfiguration = {
    new UpdateConfiguration(retrieve, missingOk, logging, artifactFilter, offline, frozen)
  }
  def withRetrieve(retrieve: Option[sbt.internal.librarymanagement.RetrieveConfiguration]): UpdateConfiguration = {
    copy(retrieve = retrieve)
  }
  def withRetrieve(retrieve: sbt.internal.librarymanagement.RetrieveConfiguration): UpdateConfiguration = {
    copy(retrieve = Option(retrieve))
  }
  def withMissingOk(missingOk: Option[Boolean]): UpdateConfiguration = {
    copy(missingOk = missingOk)
  }
  def withMissingOk(missingOk: Boolean): UpdateConfiguration = {
    copy(missingOk = Option(missingOk))
  }
  def withLogging(logging: Option[sbt.librarymanagement.UpdateLogging]): UpdateConfiguration = {
    copy(logging = logging)
  }
  def withLogging(logging: sbt.librarymanagement.UpdateLogging): UpdateConfiguration = {
    copy(logging = Option(logging))
  }
  def withArtifactFilter(artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter]): UpdateConfiguration = {
    copy(artifactFilter = artifactFilter)
  }
  def withArtifactFilter(artifactFilter: sbt.librarymanagement.ArtifactTypeFilter): UpdateConfiguration = {
    copy(artifactFilter = Option(artifactFilter))
  }
  def withOffline(offline: Option[Boolean]): UpdateConfiguration = {
    copy(offline = offline)
  }
  def withOffline(offline: Boolean): UpdateConfiguration = {
    copy(offline = Option(offline))
  }
  def withFrozen(frozen: Option[Boolean]): UpdateConfiguration = {
    copy(frozen = frozen)
  }
  def withFrozen(frozen: Boolean): UpdateConfiguration = {
    copy(frozen = Option(frozen))
  }
}
object UpdateConfiguration {
  
  def apply(): UpdateConfiguration = new UpdateConfiguration(None, None, None, None, None, None)
  def apply(retrieve: Option[sbt.internal.librarymanagement.RetrieveConfiguration], missingOk: Option[Boolean], logging: Option[sbt.librarymanagement.UpdateLogging], artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter], offline: Option[Boolean], frozen: Option[Boolean]): UpdateConfiguration = new UpdateConfiguration(retrieve, missingOk, logging, artifactFilter, offline, frozen)
  def apply(retrieve: sbt.internal.librarymanagement.RetrieveConfiguration, missingOk: Boolean, logging: sbt.librarymanagement.UpdateLogging, artifactFilter: sbt.librarymanagement.ArtifactTypeFilter, offline: Boolean, frozen: Boolean): UpdateConfiguration = new UpdateConfiguration(Option(retrieve), Option(missingOk), Option(logging), Option(artifactFilter), Option(offline), Option(frozen))
}
