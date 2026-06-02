/**
 * This code is generated using [[https://www.scala-sbt.org/contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
abstract class ModuleSettings(
  val validate: Boolean,
  val scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo]) extends Serializable {
  
  def this() = this(false, None)
  
  
  override def toString: String = {
    "ModuleSettings(" + validate + ", " + scalaModuleInfo + ")"
  }
}
object ModuleSettings {
  
}
