/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class UpdateConfiguration private (
  /**
   * If set to some RetrieveConfiguration, this enables retrieving dependencies to the specified directory.
   * Otherwise, dependencies are used directly from the cache.
   */
  val retrieveManaged: Option[sbt.librarymanagement.RetrieveConfiguration],
  /**
   * If set to true, it ignores when artifacts are missing.
   * This setting could be uses when retrieving source/javadocs jars opportunistically.
   */
  val missingOk: Option[Boolean],
  /** Logging setting used specifially for library management. */
  val logging: Option[sbt.librarymanagement.UpdateLogging],
  /** The clock that may be used for caching */
  val logicalClock: Option[sbt.librarymanagement.LogicalClock],
  val artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter],
  val offline: Option[Boolean],
  val frozen: Option[Boolean]) extends Serializable {
  
  private def this() = this(None, None, None, None, None, None, None)
  
  override def equals(o: Any): Boolean = o match {
    case x: UpdateConfiguration => (this.retrieveManaged == x.retrieveManaged) && (this.missingOk == x.missingOk) && (this.logging == x.logging) && (this.logicalClock == x.logicalClock) && (this.artifactFilter == x.artifactFilter) && (this.offline == x.offline) && (this.frozen == x.frozen)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "UpdateConfiguration".##) + retrieveManaged.##) + missingOk.##) + logging.##) + logicalClock.##) + artifactFilter.##) + offline.##) + frozen.##)
  }
  override def toString: String = {
    "UpdateConfiguration(" + retrieveManaged + ", " + missingOk + ", " + logging + ", " + logicalClock + ", " + artifactFilter + ", " + offline + ", " + frozen + ")"
  }
  protected[this] def copy(retrieveManaged: Option[sbt.librarymanagement.RetrieveConfiguration] = retrieveManaged, missingOk: Option[Boolean] = missingOk, logging: Option[sbt.librarymanagement.UpdateLogging] = logging, logicalClock: Option[sbt.librarymanagement.LogicalClock] = logicalClock, artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter] = artifactFilter, offline: Option[Boolean] = offline, frozen: Option[Boolean] = frozen): UpdateConfiguration = {
    new UpdateConfiguration(retrieveManaged, missingOk, logging, logicalClock, artifactFilter, offline, frozen)
  }
  def withRetrieveManaged(retrieveManaged: Option[sbt.librarymanagement.RetrieveConfiguration]): UpdateConfiguration = {
    copy(retrieveManaged = retrieveManaged)
  }
  def withRetrieveManaged(retrieveManaged: sbt.librarymanagement.RetrieveConfiguration): UpdateConfiguration = {
    copy(retrieveManaged = Option(retrieveManaged))
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
  def withLogicalClock(logicalClock: Option[sbt.librarymanagement.LogicalClock]): UpdateConfiguration = {
    copy(logicalClock = logicalClock)
  }
  def withLogicalClock(logicalClock: sbt.librarymanagement.LogicalClock): UpdateConfiguration = {
    copy(logicalClock = Option(logicalClock))
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
  
  def apply(): UpdateConfiguration = new UpdateConfiguration(None, None, None, None, None, None, None)
  def apply(retrieveManaged: Option[sbt.librarymanagement.RetrieveConfiguration], missingOk: Option[Boolean], logging: Option[sbt.librarymanagement.UpdateLogging], logicalClock: Option[sbt.librarymanagement.LogicalClock], artifactFilter: Option[sbt.librarymanagement.ArtifactTypeFilter], offline: Option[Boolean], frozen: Option[Boolean]): UpdateConfiguration = new UpdateConfiguration(retrieveManaged, missingOk, logging, logicalClock, artifactFilter, offline, frozen)
  def apply(retrieveManaged: sbt.librarymanagement.RetrieveConfiguration, missingOk: Boolean, logging: sbt.librarymanagement.UpdateLogging, logicalClock: sbt.librarymanagement.LogicalClock, artifactFilter: sbt.librarymanagement.ArtifactTypeFilter, offline: Boolean, frozen: Boolean): UpdateConfiguration = new UpdateConfiguration(Option(retrieveManaged), Option(missingOk), Option(logging), Option(logicalClock), Option(artifactFilter), Option(offline), Option(frozen))
}
