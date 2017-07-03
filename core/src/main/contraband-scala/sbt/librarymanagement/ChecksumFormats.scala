/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait ChecksumFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val ChecksumFormat: JsonFormat[sbt.librarymanagement.Checksum] = new JsonFormat[sbt.librarymanagement.Checksum] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.Checksum = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val digest = unbuilder.readField[String]("digest")
      val `type` = unbuilder.readField[String]("type")
      unbuilder.endObject()
      sbt.librarymanagement.Checksum(digest, `type`)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.Checksum, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("digest", obj.digest)
    builder.addField("type", obj.`type`)
    builder.endObject()
  }
}
}
