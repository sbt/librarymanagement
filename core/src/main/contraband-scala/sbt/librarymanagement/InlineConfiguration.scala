/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class InlineConfiguration private (
  validate: Boolean,
  scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo],
  val module: sbt.librarymanagement.ModuleID,
  val moduleInfo: sbt.librarymanagement.ModuleInfo,
  val dependencies: Vector[sbt.librarymanagement.ModuleID],
  val overrides: Set[sbt.librarymanagement.ModuleID],
  val excludes: Vector[sbt.librarymanagement.InclExclRule],
  val ivyXML: scala.xml.NodeSeq,
  val configurations: Vector[sbt.librarymanagement.Configuration],
  val defaultConfiguration: Option[sbt.librarymanagement.Configuration],
  val conflictManager: sbt.librarymanagement.ConflictManager) extends sbt.librarymanagement.ModuleSettings(validate, scalaModuleInfo) with Serializable {
  
  private def this(validate: Boolean, scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo], module: sbt.librarymanagement.ModuleID, moduleInfo: sbt.librarymanagement.ModuleInfo, dependencies: Vector[sbt.librarymanagement.ModuleID]) = this(validate, scalaModuleInfo, module, moduleInfo, dependencies, Set.empty, Vector.empty, scala.xml.NodeSeq.Empty, Vector.empty, None, sbt.librarymanagement.ConflictManager.default)
  
  override def equals(o: Any): Boolean = o match {
    case x: InlineConfiguration => (this.validate == x.validate) && (this.scalaModuleInfo == x.scalaModuleInfo) && (this.module == x.module) && (this.moduleInfo == x.moduleInfo) && (this.dependencies == x.dependencies) && (this.overrides == x.overrides) && (this.excludes == x.excludes) && (this.ivyXML == x.ivyXML) && (this.configurations == x.configurations) && (this.defaultConfiguration == x.defaultConfiguration) && (this.conflictManager == x.conflictManager)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "InlineConfiguration".##) + validate.##) + scalaModuleInfo.##) + module.##) + moduleInfo.##) + dependencies.##) + overrides.##) + excludes.##) + ivyXML.##) + configurations.##) + defaultConfiguration.##) + conflictManager.##)
  }
  override def toString: String = {
    "InlineConfiguration(" + validate + ", " + scalaModuleInfo + ", " + module + ", " + moduleInfo + ", " + dependencies + ", " + overrides + ", " + excludes + ", " + ivyXML + ", " + configurations + ", " + defaultConfiguration + ", " + conflictManager + ")"
  }
  protected[this] def copy(validate: Boolean = validate, scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo] = scalaModuleInfo, module: sbt.librarymanagement.ModuleID = module, moduleInfo: sbt.librarymanagement.ModuleInfo = moduleInfo, dependencies: Vector[sbt.librarymanagement.ModuleID] = dependencies, overrides: Set[sbt.librarymanagement.ModuleID] = overrides, excludes: Vector[sbt.librarymanagement.InclExclRule] = excludes, ivyXML: scala.xml.NodeSeq = ivyXML, configurations: Vector[sbt.librarymanagement.Configuration] = configurations, defaultConfiguration: Option[sbt.librarymanagement.Configuration] = defaultConfiguration, conflictManager: sbt.librarymanagement.ConflictManager = conflictManager): InlineConfiguration = {
    new InlineConfiguration(validate, scalaModuleInfo, module, moduleInfo, dependencies, overrides, excludes, ivyXML, configurations, defaultConfiguration, conflictManager)
  }
  def withValidate(validate: Boolean): InlineConfiguration = {
    copy(validate = validate)
  }
  def withScalaModuleInfo(scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo]): InlineConfiguration = {
    copy(scalaModuleInfo = scalaModuleInfo)
  }
  def withScalaModuleInfo(scalaModuleInfo: sbt.librarymanagement.ScalaModuleInfo): InlineConfiguration = {
    copy(scalaModuleInfo = Option(scalaModuleInfo))
  }
  def withModule(module: sbt.librarymanagement.ModuleID): InlineConfiguration = {
    copy(module = module)
  }
  def withModuleInfo(moduleInfo: sbt.librarymanagement.ModuleInfo): InlineConfiguration = {
    copy(moduleInfo = moduleInfo)
  }
  def withDependencies(dependencies: Vector[sbt.librarymanagement.ModuleID]): InlineConfiguration = {
    copy(dependencies = dependencies)
  }
  def withOverrides(overrides: Set[sbt.librarymanagement.ModuleID]): InlineConfiguration = {
    copy(overrides = overrides)
  }
  def withExcludes(excludes: Vector[sbt.librarymanagement.InclExclRule]): InlineConfiguration = {
    copy(excludes = excludes)
  }
  def withIvyXML(ivyXML: scala.xml.NodeSeq): InlineConfiguration = {
    copy(ivyXML = ivyXML)
  }
  def withConfigurations(configurations: Vector[sbt.librarymanagement.Configuration]): InlineConfiguration = {
    copy(configurations = configurations)
  }
  def withDefaultConfiguration(defaultConfiguration: Option[sbt.librarymanagement.Configuration]): InlineConfiguration = {
    copy(defaultConfiguration = defaultConfiguration)
  }
  def withDefaultConfiguration(defaultConfiguration: sbt.librarymanagement.Configuration): InlineConfiguration = {
    copy(defaultConfiguration = Option(defaultConfiguration))
  }
  def withConflictManager(conflictManager: sbt.librarymanagement.ConflictManager): InlineConfiguration = {
    copy(conflictManager = conflictManager)
  }
}
object InlineConfiguration extends sbt.internal.librarymanagement.InlineConfigurationFunctions {
  
  def apply(validate: Boolean, scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo], module: sbt.librarymanagement.ModuleID, moduleInfo: sbt.librarymanagement.ModuleInfo, dependencies: Vector[sbt.librarymanagement.ModuleID]): InlineConfiguration = new InlineConfiguration(validate, scalaModuleInfo, module, moduleInfo, dependencies, Set.empty, Vector.empty, scala.xml.NodeSeq.Empty, Vector.empty, None, sbt.librarymanagement.ConflictManager.default)
  def apply(validate: Boolean, scalaModuleInfo: sbt.librarymanagement.ScalaModuleInfo, module: sbt.librarymanagement.ModuleID, moduleInfo: sbt.librarymanagement.ModuleInfo, dependencies: Vector[sbt.librarymanagement.ModuleID]): InlineConfiguration = new InlineConfiguration(validate, Option(scalaModuleInfo), module, moduleInfo, dependencies, Set.empty, Vector.empty, scala.xml.NodeSeq.Empty, Vector.empty, None, sbt.librarymanagement.ConflictManager.default)
  def apply(validate: Boolean, scalaModuleInfo: Option[sbt.librarymanagement.ScalaModuleInfo], module: sbt.librarymanagement.ModuleID, moduleInfo: sbt.librarymanagement.ModuleInfo, dependencies: Vector[sbt.librarymanagement.ModuleID], overrides: Set[sbt.librarymanagement.ModuleID], excludes: Vector[sbt.librarymanagement.InclExclRule], ivyXML: scala.xml.NodeSeq, configurations: Vector[sbt.librarymanagement.Configuration], defaultConfiguration: Option[sbt.librarymanagement.Configuration], conflictManager: sbt.librarymanagement.ConflictManager): InlineConfiguration = new InlineConfiguration(validate, scalaModuleInfo, module, moduleInfo, dependencies, overrides, excludes, ivyXML, configurations, defaultConfiguration, conflictManager)
  def apply(validate: Boolean, scalaModuleInfo: sbt.librarymanagement.ScalaModuleInfo, module: sbt.librarymanagement.ModuleID, moduleInfo: sbt.librarymanagement.ModuleInfo, dependencies: Vector[sbt.librarymanagement.ModuleID], overrides: Set[sbt.librarymanagement.ModuleID], excludes: Vector[sbt.librarymanagement.InclExclRule], ivyXML: scala.xml.NodeSeq, configurations: Vector[sbt.librarymanagement.Configuration], defaultConfiguration: sbt.librarymanagement.Configuration, conflictManager: sbt.librarymanagement.ConflictManager): InlineConfiguration = new InlineConfiguration(validate, Option(scalaModuleInfo), module, moduleInfo, dependencies, overrides, excludes, ivyXML, configurations, Option(defaultConfiguration), conflictManager)
}
