/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait UpdateConfigurationFormats { self: sbt.librarymanagement.RetrieveConfigurationFormats with sbt.librarymanagement.UpdateLoggingFormats with sbt.internal.librarymanagement.formats.LogicalClockFormats with sbt.librarymanagement.ArtifactTypeFilterFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val UpdateConfigurationFormat: JsonFormat[sbt.librarymanagement.UpdateConfiguration] = new JsonFormat[sbt.librarymanagement.UpdateConfiguration] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.UpdateConfiguration = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val retrieveManaged = unbuilder.readField[Option[sbt.librarymanagement.RetrieveConfiguration]]("retrieveManaged")
      val missingOk = unbuilder.readField[Option[Boolean]]("missingOk")
      val logging = unbuilder.readField[Option[sbt.librarymanagement.UpdateLogging]]("logging")
      val logicalClock = unbuilder.readField[Option[sbt.librarymanagement.LogicalClock]]("logicalClock")
      val metadataDirectory = unbuilder.readField[Option[java.io.File]]("metadataDirectory")
      val artifactFilter = unbuilder.readField[Option[sbt.librarymanagement.ArtifactTypeFilter]]("artifactFilter")
      val offline = unbuilder.readField[Option[Boolean]]("offline")
      val frozen = unbuilder.readField[Option[Boolean]]("frozen")
      unbuilder.endObject()
      sbt.librarymanagement.UpdateConfiguration(retrieveManaged, missingOk, logging, logicalClock, metadataDirectory, artifactFilter, offline, frozen)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.UpdateConfiguration, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("retrieveManaged", obj.retrieveManaged)
    builder.addField("missingOk", obj.missingOk)
    builder.addField("logging", obj.logging)
    builder.addField("logicalClock", obj.logicalClock)
    builder.addField("metadataDirectory", obj.metadataDirectory)
    builder.addField("artifactFilter", obj.artifactFilter)
    builder.addField("offline", obj.offline)
    builder.addField("frozen", obj.frozen)
    builder.endObject()
  }
}
}
