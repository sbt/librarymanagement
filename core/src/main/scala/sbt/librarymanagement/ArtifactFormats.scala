// format: off
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait ArtifactFormats { self: sbt.librarymanagement.ConfigRefFormats with sbt.librarymanagement.ChecksumFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val ArtifactFormat: JsonFormat[sbt.librarymanagement.Artifact] = new JsonFormat[sbt.librarymanagement.Artifact] {
  override def read[J](__jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.Artifact = {
    __jsOpt match {
      case Some(__js) =>
        unbuilder.beginObject(__js)
        val name = unbuilder.readField[String]("name")
        val `type` = unbuilder.readField[String]("type")
        val extension = unbuilder.readField[String]("extension")
        val classifier = unbuilder.readField[Option[String]]("classifier")
        val configurations = unbuilder.readField[Vector[sbt.librarymanagement.ConfigRef]]("configurations")
        // for backwards compatibility, we need to read the url field instead of uri
        val url = unbuilder.readField[Option[java.net.URI]]("url")
        val extraAttributes = unbuilder.readField[Map[String, String]]("extraAttributes")
        val checksum = unbuilder.readField[Option[sbt.librarymanagement.Checksum]]("checksum")
        val allowInsecureProtocol = unbuilder.readField[Boolean]("allowInsecureProtocol")
        unbuilder.endObject()
        sbt.librarymanagement.Artifact(name, `type`, extension, classifier, configurations, url, extraAttributes, checksum, allowInsecureProtocol)
      case None =>
        deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.Artifact, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("name", obj.name)
    builder.addField("type", obj.`type`)
    builder.addField("extension", obj.extension)
    builder.addField("classifier", obj.classifier)
    builder.addField("configurations", obj.configurations)
    // for backwards compatibility, we need to write the url field instead of uri
    builder.addField("url", obj.uri)
    builder.addField("extraAttributes", obj.extraAttributes)
    builder.addField("checksum", obj.checksum)
    builder.addField("allowInsecureProtocol", obj.allowInsecureProtocol)
    builder.endObject()
  }
}
}
// format: on
