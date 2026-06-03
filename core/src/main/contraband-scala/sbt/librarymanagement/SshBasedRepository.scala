/**
 * This code is generated using [[https://www.scala-sbt.org/contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** sbt interface for an Ivy ssh-based repository (ssh and sftp).  Requires the Jsch library.. */
abstract class SshBasedRepository(
  name: String,
  patterns: sbt.librarymanagement.Patterns,
  val connection: sbt.librarymanagement.SshConnection) extends sbt.librarymanagement.PatternsBasedRepository(name, patterns) with sbt.librarymanagement.SshBasedRepositoryExtra with Serializable {
  
  
  
  
  override def toString: String = {
    "SshBasedRepository(" + name + ", " + patterns + ", " + connection + ")"
  }
}
object SshBasedRepository {
  
}
