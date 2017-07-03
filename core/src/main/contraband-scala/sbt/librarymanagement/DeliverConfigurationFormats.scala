/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait DeliverConfigurationFormats { self: sbt.librarymanagement.UpdateLoggingFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val DeliverConfigurationFormat: JsonFormat[sbt.librarymanagement.DeliverConfiguration] = new JsonFormat[sbt.librarymanagement.DeliverConfiguration] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.DeliverConfiguration = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val deliverIvyPattern = unbuilder.readField[Option[String]]("deliverIvyPattern")
      val status = unbuilder.readField[Option[String]]("status")
      val configurations = unbuilder.readField[Option[scala.Vector[String]]]("configurations")
      val logging = unbuilder.readField[Option[sbt.librarymanagement.UpdateLogging]]("logging")
      unbuilder.endObject()
      sbt.librarymanagement.DeliverConfiguration(deliverIvyPattern, status, configurations, logging)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.DeliverConfiguration, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("deliverIvyPattern", obj.deliverIvyPattern)
    builder.addField("status", obj.status)
    builder.addField("configurations", obj.configurations)
    builder.addField("logging", obj.logging)
    builder.endObject()
  }
}
}
