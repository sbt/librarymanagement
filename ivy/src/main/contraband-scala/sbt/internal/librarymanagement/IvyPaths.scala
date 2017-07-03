/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.librarymanagement
final class IvyPaths private (
  val baseDirectory: java.io.File,
  val ivyHome: Option[java.io.File]) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: IvyPaths => (this.baseDirectory == x.baseDirectory) && (this.ivyHome == x.ivyHome)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (17 + "IvyPaths".##) + baseDirectory.##) + ivyHome.##)
  }
  override def toString: String = {
    "IvyPaths(" + baseDirectory + ", " + ivyHome + ")"
  }
  protected[this] def copy(baseDirectory: java.io.File = baseDirectory, ivyHome: Option[java.io.File] = ivyHome): IvyPaths = {
    new IvyPaths(baseDirectory, ivyHome)
  }
  def withBaseDirectory(baseDirectory: java.io.File): IvyPaths = {
    copy(baseDirectory = baseDirectory)
  }
  def withIvyHome(ivyHome: Option[java.io.File]): IvyPaths = {
    copy(ivyHome = ivyHome)
  }
  def withIvyHome(ivyHome: java.io.File): IvyPaths = {
    copy(ivyHome = Option(ivyHome))
  }
}
object IvyPaths {
  
  def apply(baseDirectory: java.io.File, ivyHome: Option[java.io.File]): IvyPaths = new IvyPaths(baseDirectory, ivyHome)
  def apply(baseDirectory: java.io.File, ivyHome: java.io.File): IvyPaths = new IvyPaths(baseDirectory, Option(ivyHome))
}
