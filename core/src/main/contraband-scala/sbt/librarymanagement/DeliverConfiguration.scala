/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class DeliverConfiguration private (
  val deliverIvyPattern: Option[String],
  val status: Option[String],
  val configurations: Option[scala.Vector[sbt.librarymanagement.ConfigRef]],
  val logging: Option[sbt.librarymanagement.UpdateLogging]) extends Serializable {
  
  private def this() = this(None, None, None, None)
  
  override def equals(o: Any): Boolean = o match {
    case x: DeliverConfiguration => (this.deliverIvyPattern == x.deliverIvyPattern) && (this.status == x.status) && (this.configurations == x.configurations) && (this.logging == x.logging)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (17 + "DeliverConfiguration".##) + deliverIvyPattern.##) + status.##) + configurations.##) + logging.##)
  }
  override def toString: String = {
    "DeliverConfiguration(" + deliverIvyPattern + ", " + status + ", " + configurations + ", " + logging + ")"
  }
  protected[this] def copy(deliverIvyPattern: Option[String] = deliverIvyPattern, status: Option[String] = status, configurations: Option[scala.Vector[sbt.librarymanagement.ConfigRef]] = configurations, logging: Option[sbt.librarymanagement.UpdateLogging] = logging): DeliverConfiguration = {
    new DeliverConfiguration(deliverIvyPattern, status, configurations, logging)
  }
  def withDeliverIvyPattern(deliverIvyPattern: Option[String]): DeliverConfiguration = {
    copy(deliverIvyPattern = deliverIvyPattern)
  }
  def withDeliverIvyPattern(deliverIvyPattern: String): DeliverConfiguration = {
    copy(deliverIvyPattern = Option(deliverIvyPattern))
  }
  def withStatus(status: Option[String]): DeliverConfiguration = {
    copy(status = status)
  }
  def withStatus(status: String): DeliverConfiguration = {
    copy(status = Option(status))
  }
  def withConfigurations(configurations: Option[scala.Vector[sbt.librarymanagement.ConfigRef]]): DeliverConfiguration = {
    copy(configurations = configurations)
  }
  def withConfigurations(configurations: scala.Vector[sbt.librarymanagement.ConfigRef]): DeliverConfiguration = {
    copy(configurations = Option(configurations))
  }
  def withLogging(logging: Option[sbt.librarymanagement.UpdateLogging]): DeliverConfiguration = {
    copy(logging = logging)
  }
  def withLogging(logging: sbt.librarymanagement.UpdateLogging): DeliverConfiguration = {
    copy(logging = Option(logging))
  }
}
object DeliverConfiguration {
  
  def apply(): DeliverConfiguration = new DeliverConfiguration(None, None, None, None)
  def apply(deliverIvyPattern: Option[String], status: Option[String], configurations: Option[scala.Vector[sbt.librarymanagement.ConfigRef]], logging: Option[sbt.librarymanagement.UpdateLogging]): DeliverConfiguration = new DeliverConfiguration(deliverIvyPattern, status, configurations, logging)
  def apply(deliverIvyPattern: String, status: String, configurations: scala.Vector[sbt.librarymanagement.ConfigRef], logging: sbt.librarymanagement.UpdateLogging): DeliverConfiguration = new DeliverConfiguration(Option(deliverIvyPattern), Option(status), Option(configurations), Option(logging))
}
