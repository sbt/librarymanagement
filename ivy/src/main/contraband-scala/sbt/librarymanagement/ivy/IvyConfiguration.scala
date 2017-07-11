/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement.ivy
abstract class IvyConfiguration(
  val log: xsbti.Logger,
  val lock: Option[xsbti.GlobalLock],
  val baseDirectory: java.io.File,
  val ivyHome: Option[java.io.File],
  val updateOptions: sbt.librarymanagement.ivy.UpdateOptions) extends Serializable {
  
  def this(log: xsbti.Logger) = this(log, scala.None, new java.io.File("."), scala.None, sbt.librarymanagement.ivy.UpdateOptions())
  
  
  override def equals(o: Any): Boolean = o match {
    case x: IvyConfiguration => (this.log == x.log) && (this.lock == x.lock) && (this.baseDirectory == x.baseDirectory) && (this.ivyHome == x.ivyHome) && (this.updateOptions == x.updateOptions)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (17 + "IvyConfiguration".##) + log.##) + lock.##) + baseDirectory.##) + ivyHome.##) + updateOptions.##)
  }
  override def toString: String = {
    "IvyConfiguration(" + log + ", " + lock + ", " + baseDirectory + ", " + ivyHome + ", " + updateOptions + ")"
  }
}
object IvyConfiguration {
  
}
