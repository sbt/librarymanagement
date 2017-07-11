/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait ExternalIvyConfigurationFormats { self: sbt.internal.librarymanagement.formats.LoggerFormat with sbt.internal.librarymanagement.formats.GlobalLockFormat with sbt.librarymanagement.ivy.formats.UpdateOptionsFormat with sbt.librarymanagement.ResolverFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val ExternalIvyConfigurationFormat: JsonFormat[sbt.librarymanagement.ivy.ExternalIvyConfiguration] = new JsonFormat[sbt.librarymanagement.ivy.ExternalIvyConfiguration] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.ivy.ExternalIvyConfiguration = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val log = unbuilder.readField[xsbti.Logger]("log")
      val lock = unbuilder.readField[Option[xsbti.GlobalLock]]("lock")
      val baseDirectory = unbuilder.readField[java.io.File]("baseDirectory")
      val ivyHome = unbuilder.readField[Option[java.io.File]]("ivyHome")
      val updateOptions = unbuilder.readField[sbt.librarymanagement.ivy.UpdateOptions]("updateOptions")
      val uri = unbuilder.readField[java.net.URI]("uri")
      val extraResolvers = unbuilder.readField[Vector[sbt.librarymanagement.Resolver]]("extraResolvers")
      unbuilder.endObject()
      sbt.librarymanagement.ivy.ExternalIvyConfiguration(log, lock, baseDirectory, ivyHome, updateOptions, uri, extraResolvers)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.ivy.ExternalIvyConfiguration, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("log", obj.log)
    builder.addField("lock", obj.lock)
    builder.addField("baseDirectory", obj.baseDirectory)
    builder.addField("ivyHome", obj.ivyHome)
    builder.addField("updateOptions", obj.updateOptions)
    builder.addField("uri", obj.uri)
    builder.addField("extraResolvers", obj.extraResolvers)
    builder.endObject()
  }
}
}
