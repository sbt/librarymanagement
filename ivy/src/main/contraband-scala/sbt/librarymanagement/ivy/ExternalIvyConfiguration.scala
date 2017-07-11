/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement.ivy
final class ExternalIvyConfiguration private (
  log: xsbti.Logger,
  lock: Option[xsbti.GlobalLock],
  baseDirectory: java.io.File,
  ivyHome: Option[java.io.File],
  updateOptions: sbt.librarymanagement.ivy.UpdateOptions,
  val uri: java.net.URI,
  val extraResolvers: Vector[sbt.librarymanagement.Resolver]) extends sbt.librarymanagement.ivy.IvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions) with Serializable {
  
  private def this(log: xsbti.Logger, uri: java.net.URI, extraResolvers: Vector[sbt.librarymanagement.Resolver]) = this(log, scala.None, new java.io.File("."), scala.None, sbt.librarymanagement.ivy.UpdateOptions(), uri, extraResolvers)
  
  override def equals(o: Any): Boolean = o match {
    case x: ExternalIvyConfiguration => (this.log == x.log) && (this.lock == x.lock) && (this.baseDirectory == x.baseDirectory) && (this.ivyHome == x.ivyHome) && (this.updateOptions == x.updateOptions) && (this.uri == x.uri) && (this.extraResolvers == x.extraResolvers)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "ExternalIvyConfiguration".##) + log.##) + lock.##) + baseDirectory.##) + ivyHome.##) + updateOptions.##) + uri.##) + extraResolvers.##)
  }
  override def toString: String = {
    "ExternalIvyConfiguration(" + log + ", " + lock + ", " + baseDirectory + ", " + ivyHome + ", " + updateOptions + ", " + uri + ", " + extraResolvers + ")"
  }
  protected[this] def copy(log: xsbti.Logger = log, lock: Option[xsbti.GlobalLock] = lock, baseDirectory: java.io.File = baseDirectory, ivyHome: Option[java.io.File] = ivyHome, updateOptions: sbt.librarymanagement.ivy.UpdateOptions = updateOptions, uri: java.net.URI = uri, extraResolvers: Vector[sbt.librarymanagement.Resolver] = extraResolvers): ExternalIvyConfiguration = {
    new ExternalIvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions, uri, extraResolvers)
  }
  def withLog(log: xsbti.Logger): ExternalIvyConfiguration = {
    copy(log = log)
  }
  def withLock(lock: Option[xsbti.GlobalLock]): ExternalIvyConfiguration = {
    copy(lock = lock)
  }
  def withLock(lock: xsbti.GlobalLock): ExternalIvyConfiguration = {
    copy(lock = Option(lock))
  }
  def withBaseDirectory(baseDirectory: java.io.File): ExternalIvyConfiguration = {
    copy(baseDirectory = baseDirectory)
  }
  def withIvyHome(ivyHome: Option[java.io.File]): ExternalIvyConfiguration = {
    copy(ivyHome = ivyHome)
  }
  def withIvyHome(ivyHome: java.io.File): ExternalIvyConfiguration = {
    copy(ivyHome = Option(ivyHome))
  }
  def withUpdateOptions(updateOptions: sbt.librarymanagement.ivy.UpdateOptions): ExternalIvyConfiguration = {
    copy(updateOptions = updateOptions)
  }
  def withUri(uri: java.net.URI): ExternalIvyConfiguration = {
    copy(uri = uri)
  }
  def withExtraResolvers(extraResolvers: Vector[sbt.librarymanagement.Resolver]): ExternalIvyConfiguration = {
    copy(extraResolvers = extraResolvers)
  }
}
object ExternalIvyConfiguration {
  
  def apply(log: xsbti.Logger, uri: java.net.URI, extraResolvers: Vector[sbt.librarymanagement.Resolver]): ExternalIvyConfiguration = new ExternalIvyConfiguration(log, scala.None, new java.io.File("."), scala.None, sbt.librarymanagement.ivy.UpdateOptions(), uri, extraResolvers)
  def apply(log: xsbti.Logger, lock: Option[xsbti.GlobalLock], baseDirectory: java.io.File, ivyHome: Option[java.io.File], updateOptions: sbt.librarymanagement.ivy.UpdateOptions, uri: java.net.URI, extraResolvers: Vector[sbt.librarymanagement.Resolver]): ExternalIvyConfiguration = new ExternalIvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions, uri, extraResolvers)
  def apply(log: xsbti.Logger, lock: xsbti.GlobalLock, baseDirectory: java.io.File, ivyHome: java.io.File, updateOptions: sbt.librarymanagement.ivy.UpdateOptions, uri: java.net.URI, extraResolvers: Vector[sbt.librarymanagement.Resolver]): ExternalIvyConfiguration = new ExternalIvyConfiguration(log, Option(lock), baseDirectory, Option(ivyHome), updateOptions, uri, extraResolvers)
}
