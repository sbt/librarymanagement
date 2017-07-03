/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** Provides information about resolution of a single configuration. */
final class ConfigurationReport private (
  /** the configuration this report is for. */
  val configuration: String,
  /** a sequence containing one report for each module resolved for this configuration. */
  val modules: Vector[sbt.librarymanagement.ModuleReport],
  /** a sequence containing one report for each org/name, which may or may not be part of the final resolution. */
  val details: Vector[sbt.librarymanagement.OrganizationArtifactReport]) extends sbt.librarymanagement.ConfigurationReportExtra with Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: ConfigurationReport => (this.configuration == x.configuration) && (this.modules == x.modules) && (this.details == x.details)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (17 + "ConfigurationReport".##) + configuration.##) + modules.##) + details.##)
  }
  override def toString: String = {
    s"\t$configuration:\n" +
    (if (details.isEmpty) modules.mkString + details.flatMap(_.modules).filter(_.evicted).map("\t\t(EVICTED) " + _ + "\n").mkString
    else details.mkString)
  }
  protected[this] def copy(configuration: String = configuration, modules: Vector[sbt.librarymanagement.ModuleReport] = modules, details: Vector[sbt.librarymanagement.OrganizationArtifactReport] = details): ConfigurationReport = {
    new ConfigurationReport(configuration, modules, details)
  }
  def withConfiguration(configuration: String): ConfigurationReport = {
    copy(configuration = configuration)
  }
  def withModules(modules: Vector[sbt.librarymanagement.ModuleReport]): ConfigurationReport = {
    copy(modules = modules)
  }
  def withDetails(details: Vector[sbt.librarymanagement.OrganizationArtifactReport]): ConfigurationReport = {
    copy(details = details)
  }
}
object ConfigurationReport {
  
  def apply(configuration: String, modules: Vector[sbt.librarymanagement.ModuleReport], details: Vector[sbt.librarymanagement.OrganizationArtifactReport]): ConfigurationReport = new ConfigurationReport(configuration, modules, details)
}
