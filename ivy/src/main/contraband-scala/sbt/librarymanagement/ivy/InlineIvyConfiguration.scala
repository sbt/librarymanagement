/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement.ivy
final class InlineIvyConfiguration private (
  log: xsbti.Logger,
  lock: Option[xsbti.GlobalLock],
  baseDirectory: java.io.File,
  ivyHome: Option[java.io.File],
  updateOptions: sbt.librarymanagement.ivy.UpdateOptions,
  val resolvers: Vector[sbt.librarymanagement.Resolver],
  val otherResolvers: Vector[sbt.librarymanagement.Resolver],
  val moduleConfigurations: Vector[sbt.librarymanagement.ModuleConfiguration],
  val checksums: Vector[String],
  val managedChecksums: Boolean,
  val resolutionCacheDir: Option[java.io.File]) extends sbt.librarymanagement.ivy.IvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions) with Serializable {
  
  private def this(log: xsbti.Logger) = this(log, scala.None, new java.io.File("."), scala.None, sbt.librarymanagement.ivy.UpdateOptions(), sbt.librarymanagement.Resolver.defaultResolvers(Vector.empty, true, true).toVector, scala.collection.immutable.Vector.empty, scala.collection.immutable.Vector.empty, scala.collection.immutable.Vector.empty // Or IvySbt.DefaultChecksums?
  , false, scala.None)
  
  override def equals(o: Any): Boolean = o match {
    case x: InlineIvyConfiguration => (this.log == x.log) && (this.lock == x.lock) && (this.baseDirectory == x.baseDirectory) && (this.ivyHome == x.ivyHome) && (this.updateOptions == x.updateOptions) && (this.resolvers == x.resolvers) && (this.otherResolvers == x.otherResolvers) && (this.moduleConfigurations == x.moduleConfigurations) && (this.checksums == x.checksums) && (this.managedChecksums == x.managedChecksums) && (this.resolutionCacheDir == x.resolutionCacheDir)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "InlineIvyConfiguration".##) + log.##) + lock.##) + baseDirectory.##) + ivyHome.##) + updateOptions.##) + resolvers.##) + otherResolvers.##) + moduleConfigurations.##) + checksums.##) + managedChecksums.##) + resolutionCacheDir.##)
  }
  override def toString: String = {
    "InlineIvyConfiguration(" + log + ", " + lock + ", " + baseDirectory + ", " + ivyHome + ", " + updateOptions + ", " + resolvers + ", " + otherResolvers + ", " + moduleConfigurations + ", " + checksums + ", " + managedChecksums + ", " + resolutionCacheDir + ")"
  }
  protected[this] def copy(log: xsbti.Logger = log, lock: Option[xsbti.GlobalLock] = lock, baseDirectory: java.io.File = baseDirectory, ivyHome: Option[java.io.File] = ivyHome, updateOptions: sbt.librarymanagement.ivy.UpdateOptions = updateOptions, resolvers: Vector[sbt.librarymanagement.Resolver] = resolvers, otherResolvers: Vector[sbt.librarymanagement.Resolver] = otherResolvers, moduleConfigurations: Vector[sbt.librarymanagement.ModuleConfiguration] = moduleConfigurations, checksums: Vector[String] = checksums, managedChecksums: Boolean = managedChecksums, resolutionCacheDir: Option[java.io.File] = resolutionCacheDir): InlineIvyConfiguration = {
    new InlineIvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions, resolvers, otherResolvers, moduleConfigurations, checksums, managedChecksums, resolutionCacheDir)
  }
  def withLog(log: xsbti.Logger): InlineIvyConfiguration = {
    copy(log = log)
  }
  def withLock(lock: Option[xsbti.GlobalLock]): InlineIvyConfiguration = {
    copy(lock = lock)
  }
  def withLock(lock: xsbti.GlobalLock): InlineIvyConfiguration = {
    copy(lock = Option(lock))
  }
  def withBaseDirectory(baseDirectory: java.io.File): InlineIvyConfiguration = {
    copy(baseDirectory = baseDirectory)
  }
  def withIvyHome(ivyHome: Option[java.io.File]): InlineIvyConfiguration = {
    copy(ivyHome = ivyHome)
  }
  def withIvyHome(ivyHome: java.io.File): InlineIvyConfiguration = {
    copy(ivyHome = Option(ivyHome))
  }
  def withUpdateOptions(updateOptions: sbt.librarymanagement.ivy.UpdateOptions): InlineIvyConfiguration = {
    copy(updateOptions = updateOptions)
  }
  def withResolvers(resolvers: Vector[sbt.librarymanagement.Resolver]): InlineIvyConfiguration = {
    copy(resolvers = resolvers)
  }
  def withOtherResolvers(otherResolvers: Vector[sbt.librarymanagement.Resolver]): InlineIvyConfiguration = {
    copy(otherResolvers = otherResolvers)
  }
  def withModuleConfigurations(moduleConfigurations: Vector[sbt.librarymanagement.ModuleConfiguration]): InlineIvyConfiguration = {
    copy(moduleConfigurations = moduleConfigurations)
  }
  def withChecksums(checksums: Vector[String]): InlineIvyConfiguration = {
    copy(checksums = checksums)
  }
  def withManagedChecksums(managedChecksums: Boolean): InlineIvyConfiguration = {
    copy(managedChecksums = managedChecksums)
  }
  def withResolutionCacheDir(resolutionCacheDir: Option[java.io.File]): InlineIvyConfiguration = {
    copy(resolutionCacheDir = resolutionCacheDir)
  }
  def withResolutionCacheDir(resolutionCacheDir: java.io.File): InlineIvyConfiguration = {
    copy(resolutionCacheDir = Option(resolutionCacheDir))
  }
}
object InlineIvyConfiguration {
  
  def apply(log: xsbti.Logger): InlineIvyConfiguration = new InlineIvyConfiguration(log, scala.None, new java.io.File("."), scala.None, sbt.librarymanagement.ivy.UpdateOptions(), sbt.librarymanagement.Resolver.defaultResolvers(Vector.empty, true, true).toVector, scala.collection.immutable.Vector.empty, scala.collection.immutable.Vector.empty, scala.collection.immutable.Vector.empty // Or IvySbt.DefaultChecksums?
  , false, scala.None)
  def apply(log: xsbti.Logger, lock: Option[xsbti.GlobalLock], baseDirectory: java.io.File, ivyHome: Option[java.io.File], updateOptions: sbt.librarymanagement.ivy.UpdateOptions, resolvers: Vector[sbt.librarymanagement.Resolver], otherResolvers: Vector[sbt.librarymanagement.Resolver], moduleConfigurations: Vector[sbt.librarymanagement.ModuleConfiguration], checksums: Vector[String], managedChecksums: Boolean, resolutionCacheDir: Option[java.io.File]): InlineIvyConfiguration = new InlineIvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions, resolvers, otherResolvers, moduleConfigurations, checksums, managedChecksums, resolutionCacheDir)
  def apply(log: xsbti.Logger, lock: xsbti.GlobalLock, baseDirectory: java.io.File, ivyHome: java.io.File, updateOptions: sbt.librarymanagement.ivy.UpdateOptions, resolvers: Vector[sbt.librarymanagement.Resolver], otherResolvers: Vector[sbt.librarymanagement.Resolver], moduleConfigurations: Vector[sbt.librarymanagement.ModuleConfiguration], checksums: Vector[String], managedChecksums: Boolean, resolutionCacheDir: java.io.File): InlineIvyConfiguration = new InlineIvyConfiguration(log, Option(lock), baseDirectory, Option(ivyHome), updateOptions, resolvers, otherResolvers, moduleConfigurations, checksums, managedChecksums, Option(resolutionCacheDir))
}
