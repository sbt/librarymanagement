/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class RetrieveConfiguration private (
  val retrieveDirectory: Option[java.io.File],
  val outputPattern: Option[String],
  val sync: Option[Boolean],
  val configurationsToRetrieve: Option[scala.Vector[String]]) extends Serializable {
  
  private def this() = this(None, None, None, None)
  private def this(retrieveDirectory: Option[java.io.File], outputPattern: Option[String]) = this(retrieveDirectory, outputPattern, None, None)
  
  override def equals(o: Any): Boolean = o match {
    case x: RetrieveConfiguration => (this.retrieveDirectory == x.retrieveDirectory) && (this.outputPattern == x.outputPattern) && (this.sync == x.sync) && (this.configurationsToRetrieve == x.configurationsToRetrieve)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (17 + "RetrieveConfiguration".##) + retrieveDirectory.##) + outputPattern.##) + sync.##) + configurationsToRetrieve.##)
  }
  override def toString: String = {
    "RetrieveConfiguration(" + retrieveDirectory + ", " + outputPattern + ", " + sync + ", " + configurationsToRetrieve + ")"
  }
  protected[this] def copy(retrieveDirectory: Option[java.io.File] = retrieveDirectory, outputPattern: Option[String] = outputPattern, sync: Option[Boolean] = sync, configurationsToRetrieve: Option[scala.Vector[String]] = configurationsToRetrieve): RetrieveConfiguration = {
    new RetrieveConfiguration(retrieveDirectory, outputPattern, sync, configurationsToRetrieve)
  }
  def withRetrieveDirectory(retrieveDirectory: Option[java.io.File]): RetrieveConfiguration = {
    copy(retrieveDirectory = retrieveDirectory)
  }
  def withRetrieveDirectory(retrieveDirectory: java.io.File): RetrieveConfiguration = {
    copy(retrieveDirectory = Option(retrieveDirectory))
  }
  def withOutputPattern(outputPattern: Option[String]): RetrieveConfiguration = {
    copy(outputPattern = outputPattern)
  }
  def withOutputPattern(outputPattern: String): RetrieveConfiguration = {
    copy(outputPattern = Option(outputPattern))
  }
  def withSync(sync: Option[Boolean]): RetrieveConfiguration = {
    copy(sync = sync)
  }
  def withSync(sync: Boolean): RetrieveConfiguration = {
    copy(sync = Option(sync))
  }
  def withConfigurationsToRetrieve(configurationsToRetrieve: Option[scala.Vector[String]]): RetrieveConfiguration = {
    copy(configurationsToRetrieve = configurationsToRetrieve)
  }
  def withConfigurationsToRetrieve(configurationsToRetrieve: scala.Vector[String]): RetrieveConfiguration = {
    copy(configurationsToRetrieve = Option(configurationsToRetrieve))
  }
}
object RetrieveConfiguration {
  
  def apply(): RetrieveConfiguration = new RetrieveConfiguration(None, None, None, None)
  def apply(retrieveDirectory: Option[java.io.File], outputPattern: Option[String]): RetrieveConfiguration = new RetrieveConfiguration(retrieveDirectory, outputPattern, None, None)
  def apply(retrieveDirectory: java.io.File, outputPattern: String): RetrieveConfiguration = new RetrieveConfiguration(Option(retrieveDirectory), Option(outputPattern), None, None)
  def apply(retrieveDirectory: Option[java.io.File], outputPattern: Option[String], sync: Option[Boolean], configurationsToRetrieve: Option[scala.Vector[String]]): RetrieveConfiguration = new RetrieveConfiguration(retrieveDirectory, outputPattern, sync, configurationsToRetrieve)
  def apply(retrieveDirectory: java.io.File, outputPattern: String, sync: Boolean, configurationsToRetrieve: scala.Vector[String]): RetrieveConfiguration = new RetrieveConfiguration(Option(retrieveDirectory), Option(outputPattern), Option(sync), Option(configurationsToRetrieve))
}
