/**
 * This code is generated using [[https://www.scala-sbt.org/contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** sbt interface to an Ivy repository based on patterns, which is most Ivy repositories. */
abstract class PatternsBasedRepository(
  name: String,
  val patterns: sbt.librarymanagement.Patterns) extends sbt.librarymanagement.Resolver(name) with Serializable {
  
  
  
  
  override def toString: String = {
    "PatternsBasedRepository(" + name + ", " + patterns + ")"
  }
}
object PatternsBasedRepository {
  
}
