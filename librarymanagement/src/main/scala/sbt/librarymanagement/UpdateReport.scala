/* sbt -- Simple Build Tool
 * Copyright 2011  Mark Harrah
 */
package sbt.librarymanagement

import java.io.File

class RichConfigurationReport(val configurationReport: ConfigurationReport) extends AnyVal {
  import configurationReport._

  /** a sequence of evicted modules */
  def evicted: Seq[ModuleID] =
    details flatMap (_.modules) filter (_.evicted) map (_.module)

  override def toString = s"\t$configuration:\n" +
    (if (details.isEmpty) modules.mkString + details.flatMap(_.modules).filter(_.evicted).map("\t\t(EVICTED) " + _ + "\n").mkString
    else details.mkString)

  /**
   * All resolved modules for this configuration.
   * For a given organization and module name, there is only one revision/`ModuleID` in this sequence.
   */
  def allModules: Seq[ModuleID] = modules map addConfiguration
  private[this] def addConfiguration(mr: ModuleReport): ModuleID = {
    val module = mr.module
    if (module.configurations.isEmpty) {
      val conf = mr.configurations map (c => s"$configuration->$c") mkString ";"
      module.copy(configurations = Some(conf))
    } else module
  }

  def retrieve(f: (String, ModuleID, Artifact, File) => File): ConfigurationReport =
    new ConfigurationReport(configuration, modules map { _.retrieve((mid, art, file) => f(configuration, mid, art, file)) }, details)
}

class RichModuleReport(val moduleReport: ModuleReport) extends AnyVal {
  import moduleReport._

  private[this] def arts: Seq[String] = artifacts.map(_.toString) ++ missingArtifacts.map(art => "(MISSING) " + art)

  def to_s = toString
  override def toString: String = {
    s"\t\t${module.toString}: " +
      (if (arts.size <= 1) "" else "\n\t\t\t") + arts.mkString("\n\t\t\t") + "\n"
  }
  def detailReport: String =
    s"\t\t- ${module.revision}\n" +
      (if (arts.size <= 1) "" else arts.mkString("\t\t\t", "\n\t\t\t", "\n")) +
      reportStr("status", status) +
      reportStr("publicationDate", publicationDate map { _.toString }) +
      reportStr("resolver", resolver) +
      reportStr("artifactResolver", artifactResolver) +
      reportStr("evicted", Some(evicted.toString)) +
      reportStr("evictedData", evictedData) +
      reportStr("evictedReason", evictedReason) +
      reportStr("problem", problem) +
      reportStr("homepage", homepage) +
      reportStr(
        "textraAttributes",
        if (extraAttributes.isEmpty) None
        else { Some(extraAttributes.toString) }
      ) +
        reportStr("isDefault", isDefault map { _.toString }) +
        reportStr("branch", branch) +
        reportStr(
          "configurations",
          if (configurations.isEmpty) None
          else { Some(configurations.mkString(", ")) }
        ) +
          reportStr(
            "licenses",
            if (licenses.isEmpty) None
            else { Some(licenses.mkString(", ")) }
          ) +
            reportStr(
              "callers",
              if (callers.isEmpty) None
              else { Some(callers.mkString(", ")) }
            )
  private[sbt] def reportStr(key: String, value: Option[String]): String =
    value map { x => s"\t\t\t$key: $x\n" } getOrElse ""

  def retrieve(f: (ModuleID, Artifact, File) => File): ModuleReport =
    copy(artifacts = artifacts.map { case (art, file) => (art, f(module, art, file)) })
}
