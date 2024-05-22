// format: off
package sbt.librarymanagement

import _root_.sjsonnew.{Builder, JsonFormat, Unbuilder, deserializationError}

trait ScmInfoFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val ScmInfoFormat: JsonFormat[sbt.librarymanagement.ScmInfo] = new JsonFormat[sbt.librarymanagement.ScmInfo] {
  override def read[J](__jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.ScmInfo = {
    __jsOpt match {
      case Some(__js) =>
      unbuilder.beginObject(__js)
      // for backwards compatibility, we need to read the browseUrl field instead of browseUri
      val browseUrl = unbuilder.readField[java.net.URI]("browseUrl")
      val connection = unbuilder.readField[String]("connection")
      val devConnection = unbuilder.readField[Option[String]]("devConnection")
      unbuilder.endObject()
      sbt.librarymanagement.ScmInfo(browseUrl, connection, devConnection)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.ScmInfo, builder: Builder[J]): Unit = {
    builder.beginObject()
    // for backwards compatibility, we need to write the browseUrl field instead of browseUri
    builder.addField("browseUrl", obj.browseUri)
    builder.addField("connection", obj.connection)
    builder.addField("devConnection", obj.devConnection)
    builder.endObject()
  }
}
}
// format: on
