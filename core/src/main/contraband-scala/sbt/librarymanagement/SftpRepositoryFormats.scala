/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait SftpRepositoryFormats { self: sbt.librarymanagement.PatternsFormats with sbt.librarymanagement.SshConnectionFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val SftpRepositoryFormat: JsonFormat[sbt.librarymanagement.SftpRepository] = new JsonFormat[sbt.librarymanagement.SftpRepository] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.SftpRepository = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val name = unbuilder.readField[String]("name")
      val patterns = unbuilder.readField[sbt.librarymanagement.Patterns]("patterns")
      val connection = unbuilder.readField[sbt.librarymanagement.SshConnection]("connection")
      unbuilder.endObject()
      sbt.librarymanagement.SftpRepository(name, patterns, connection)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.SftpRepository, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("name", obj.name)
    builder.addField("patterns", obj.patterns)
    builder.addField("connection", obj.connection)
    builder.endObject()
  }
}
}
