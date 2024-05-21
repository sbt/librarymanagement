// format: off
package sbt.librarymanagement
import _root_.sjsonnew.{Builder, JsonFormat, Unbuilder, deserializationError}
trait DeveloperFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val DeveloperFormat: JsonFormat[sbt.librarymanagement.Developer] = new JsonFormat[sbt.librarymanagement.Developer] {
  override def read[J](__jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.Developer = {
    __jsOpt match {
      case Some(__js) =>
      unbuilder.beginObject(__js)
      val id = unbuilder.readField[String]("id")
      val name = unbuilder.readField[String]("name")
      val email = unbuilder.readField[String]("email")
      // for backwards compatibility, we need to read the url field instead of uri
      val url = unbuilder.readField[java.net.URI]("url")
      unbuilder.endObject()
      sbt.librarymanagement.Developer(id, name, email, url)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.Developer, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("id", obj.id)
    builder.addField("name", obj.name)
    builder.addField("email", obj.email)
    // for backwards compatibility, we need to write the url field instead of uri
    builder.addField("url", obj.uri)
    builder.endObject()
  }
}
}
// format: on
