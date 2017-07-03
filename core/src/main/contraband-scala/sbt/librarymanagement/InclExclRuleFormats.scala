/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait InclExclRuleFormats { self: sbt.librarymanagement.CrossVersionFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val InclExclRuleFormat: JsonFormat[sbt.librarymanagement.InclExclRule] = new JsonFormat[sbt.librarymanagement.InclExclRule] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.InclExclRule = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val organization = unbuilder.readField[String]("organization")
      val name = unbuilder.readField[String]("name")
      val artifact = unbuilder.readField[String]("artifact")
      val configurations = unbuilder.readField[Vector[String]]("configurations")
      val crossVersion = unbuilder.readField[sbt.librarymanagement.CrossVersion]("crossVersion")
      unbuilder.endObject()
      sbt.librarymanagement.InclExclRule(organization, name, artifact, configurations, crossVersion)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.InclExclRule, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("organization", obj.organization)
    builder.addField("name", obj.name)
    builder.addField("artifact", obj.artifact)
    builder.addField("configurations", obj.configurations)
    builder.addField("crossVersion", obj.crossVersion)
    builder.endObject()
  }
}
}
