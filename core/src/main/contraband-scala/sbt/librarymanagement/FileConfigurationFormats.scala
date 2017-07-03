/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait FileConfigurationFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val FileConfigurationFormat: JsonFormat[sbt.librarymanagement.FileConfiguration] = new JsonFormat[sbt.librarymanagement.FileConfiguration] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.FileConfiguration = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val isLocal = unbuilder.readField[Boolean]("isLocal")
      val isTransactional = unbuilder.readField[Option[Boolean]]("isTransactional")
      unbuilder.endObject()
      sbt.librarymanagement.FileConfiguration(isLocal, isTransactional)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.FileConfiguration, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("isLocal", obj.isLocal)
    builder.addField("isTransactional", obj.isTransactional)
    builder.endObject()
  }
}
}
