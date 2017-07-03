/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** sbt interface to an Ivy repository based on patterns, which is most Ivy repositories. */
abstract class PatternsBasedRepository(
  name: String,
  val patterns: sbt.librarymanagement.Patterns) extends sbt.librarymanagement.Resolver(name) with Serializable {
  
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: PatternsBasedRepository => (this.name == x.name) && (this.patterns == x.patterns)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (17 + "PatternsBasedRepository".##) + name.##) + patterns.##)
  }
  override def toString: String = {
    "PatternsBasedRepository(" + name + ", " + patterns + ")"
  }
}
object PatternsBasedRepository {
  
}
