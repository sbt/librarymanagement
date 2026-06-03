/**
 * This code is generated using [[https://www.scala-sbt.org/contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** An instance of a remote maven repository.  Note:  This will use Aether/Maven to resolve artifacts. */
abstract class MavenRepository(
  name: String,
  val root: String,
  val localIfFile: Boolean) extends sbt.librarymanagement.Resolver(name) with Serializable {
  def isCache: Boolean
  def allowInsecureProtocol: Boolean
  def withAllowInsecureProtocol(allowInsecureProtocol: Boolean): MavenRepository =
  this match {
    case x: MavenRepo  => x.with_allowInsecureProtocol(allowInsecureProtocol)
    case x: MavenCache => x
  }
  def this(name: String, root: String) = this(name, root, true)
  
  
  override def toString: String = {
    "MavenRepository(" + name + ", " + root + ", " + localIfFile + ")"
  }
}
object MavenRepository extends sbt.librarymanagement.MavenRepositoryFunctions {
  
}
