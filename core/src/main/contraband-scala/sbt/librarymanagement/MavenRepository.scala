/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** An instance of a remote maven repository.  Note:  This will use Aether/Maven to resolve artifacts. */
abstract class MavenRepository(
  name: String,
  val root: String,
  val localIfFile: Boolean) extends sbt.librarymanagement.Resolver(name) with Serializable {
  def isCache: Boolean
  def this(name: String, root: String) = this(name, root, true)
  
  
  override def equals(o: Any): Boolean = o match {
    case x: MavenRepository => (this.name == x.name) && (this.root == x.root) && (this.localIfFile == x.localIfFile)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (17 + "MavenRepository".##) + name.##) + root.##) + localIfFile.##)
  }
  override def toString: String = {
    "MavenRepository(" + name + ", " + root + ", " + localIfFile + ")"
  }
}
object MavenRepository extends sbt.librarymanagement.MavenRepositoryFunctions {
  
}
