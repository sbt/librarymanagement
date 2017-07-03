/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait ScalaModuleInfoFormats { self: sbt.librarymanagement.ConfigurationFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val ScalaModuleInfoFormat: JsonFormat[sbt.librarymanagement.ScalaModuleInfo] = new JsonFormat[sbt.librarymanagement.ScalaModuleInfo] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.ScalaModuleInfo = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val scalaFullVersion = unbuilder.readField[String]("scalaFullVersion")
      val scalaBinaryVersion = unbuilder.readField[String]("scalaBinaryVersion")
      val configurations = unbuilder.readField[Vector[sbt.librarymanagement.Configuration]]("configurations")
      val checkExplicit = unbuilder.readField[Boolean]("checkExplicit")
      val filterImplicit = unbuilder.readField[Boolean]("filterImplicit")
      val overrideScalaVersion = unbuilder.readField[Boolean]("overrideScalaVersion")
      val scalaOrganization = unbuilder.readField[String]("scalaOrganization")
      val scalaArtifacts = unbuilder.readField[scala.Vector[String]]("scalaArtifacts")
      unbuilder.endObject()
      sbt.librarymanagement.ScalaModuleInfo(scalaFullVersion, scalaBinaryVersion, configurations, checkExplicit, filterImplicit, overrideScalaVersion, scalaOrganization, scalaArtifacts)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.ScalaModuleInfo, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("scalaFullVersion", obj.scalaFullVersion)
    builder.addField("scalaBinaryVersion", obj.scalaBinaryVersion)
    builder.addField("configurations", obj.configurations)
    builder.addField("checkExplicit", obj.checkExplicit)
    builder.addField("filterImplicit", obj.filterImplicit)
    builder.addField("overrideScalaVersion", obj.overrideScalaVersion)
    builder.addField("scalaOrganization", obj.scalaOrganization)
    builder.addField("scalaArtifacts", obj.scalaArtifacts)
    builder.endObject()
  }
}
}
