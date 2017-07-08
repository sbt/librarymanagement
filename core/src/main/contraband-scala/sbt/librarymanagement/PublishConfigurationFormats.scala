/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait PublishConfigurationFormats { self: sbt.librarymanagement.ArtifactFormats with sbt.librarymanagement.UpdateLoggingFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val PublishConfigurationFormat: JsonFormat[sbt.librarymanagement.PublishConfiguration] = new JsonFormat[sbt.librarymanagement.PublishConfiguration] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.PublishConfiguration = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val metadataFile = unbuilder.readField[Option[java.io.File]]("metadataFile")
      val resolverName = unbuilder.readField[Option[String]]("resolverName")
      val artifacts = unbuilder.readField[Vector[scala.Tuple2[sbt.librarymanagement.Artifact, java.io.File]]]("artifacts")
      val checksums = unbuilder.readField[scala.Vector[String]]("checksums")
      val logging = unbuilder.readField[Option[sbt.librarymanagement.UpdateLogging]]("logging")
      val overwrite = unbuilder.readField[Boolean]("overwrite")
      unbuilder.endObject()
      sbt.librarymanagement.PublishConfiguration(metadataFile, resolverName, artifacts, checksums, logging, overwrite)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.PublishConfiguration, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("metadataFile", obj.metadataFile)
    builder.addField("resolverName", obj.resolverName)
    builder.addField("artifacts", obj.artifacts)
    builder.addField("checksums", obj.checksums)
    builder.addField("logging", obj.logging)
    builder.addField("overwrite", obj.overwrite)
    builder.endObject()
  }
}
}
