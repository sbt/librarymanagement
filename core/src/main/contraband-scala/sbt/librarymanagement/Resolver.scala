/**
 * This code is generated using [[https://www.scala-sbt.org/contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
abstract class Resolver(
  val name: String) extends Serializable {
  /** check for HTTP */
  private[sbt] def validateProtocol(logger: sbt.util.Logger): Boolean = false
  
  
  
  override def toString: String = {
    "Resolver(" + name + ")"
  }
}
object Resolver extends sbt.librarymanagement.ResolverFunctions {
  
}
