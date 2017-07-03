/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait SshConnectionFormats { self: sbt.librarymanagement.SshAuthenticationFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val SshConnectionFormat: JsonFormat[sbt.librarymanagement.SshConnection] = new JsonFormat[sbt.librarymanagement.SshConnection] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.SshConnection = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val authentication = unbuilder.readField[Option[sbt.librarymanagement.SshAuthentication]]("authentication")
      val hostname = unbuilder.readField[Option[String]]("hostname")
      val port = unbuilder.readField[Option[Int]]("port")
      unbuilder.endObject()
      sbt.librarymanagement.SshConnection(authentication, hostname, port)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.SshConnection, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("authentication", obj.authentication)
    builder.addField("hostname", obj.hostname)
    builder.addField("port", obj.port)
    builder.endObject()
  }
}
}
