/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait InlineIvyConfigurationFormats { self: sbt.internal.librarymanagement.formats.GlobalLockFormat with sbt.internal.librarymanagement.formats.LoggerFormat with sbt.librarymanagement.ivy.formats.UpdateOptionsFormat with sbt.librarymanagement.IvyPathsFormats with sbt.librarymanagement.ResolverFormats with sbt.librarymanagement.ModuleConfigurationFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val InlineIvyConfigurationFormat: JsonFormat[sbt.librarymanagement.ivy.InlineIvyConfiguration] = new JsonFormat[sbt.librarymanagement.ivy.InlineIvyConfiguration] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.librarymanagement.ivy.InlineIvyConfiguration = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val lock = unbuilder.readField[Option[xsbti.GlobalLock]]("lock")
      val baseDirectory = unbuilder.readField[java.io.File]("baseDirectory")
      val log = unbuilder.readField[xsbti.Logger]("log")
      val updateOptions = unbuilder.readField[sbt.librarymanagement.ivy.UpdateOptions]("updateOptions")
      val paths = unbuilder.readField[sbt.librarymanagement.ivy.IvyPaths]("paths")
      val resolvers = unbuilder.readField[Vector[sbt.librarymanagement.Resolver]]("resolvers")
      val otherResolvers = unbuilder.readField[Vector[sbt.librarymanagement.Resolver]]("otherResolvers")
      val moduleConfigurations = unbuilder.readField[Vector[sbt.librarymanagement.ModuleConfiguration]]("moduleConfigurations")
      val checksums = unbuilder.readField[Vector[String]]("checksums")
      val managedChecksums = unbuilder.readField[Boolean]("managedChecksums")
      val resolutionCacheDir = unbuilder.readField[Option[java.io.File]]("resolutionCacheDir")
      unbuilder.endObject()
      sbt.librarymanagement.ivy.InlineIvyConfiguration(lock, baseDirectory, log, updateOptions, paths, resolvers, otherResolvers, moduleConfigurations, checksums, managedChecksums, resolutionCacheDir)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.librarymanagement.ivy.InlineIvyConfiguration, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("lock", obj.lock)
    builder.addField("baseDirectory", obj.baseDirectory)
    builder.addField("log", obj.log)
    builder.addField("updateOptions", obj.updateOptions)
    builder.addField("paths", obj.paths)
    builder.addField("resolvers", obj.resolvers)
    builder.addField("otherResolvers", obj.otherResolvers)
    builder.addField("moduleConfigurations", obj.moduleConfigurations)
    builder.addField("checksums", obj.checksums)
    builder.addField("managedChecksums", obj.managedChecksums)
    builder.addField("resolutionCacheDir", obj.resolutionCacheDir)
    builder.endObject()
  }
}
}
