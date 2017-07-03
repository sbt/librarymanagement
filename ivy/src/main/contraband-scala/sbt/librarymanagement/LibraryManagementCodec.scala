/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
trait LibraryManagementCodec extends sbt.internal.librarymanagement.formats.GlobalLockFormat
  with sbt.internal.librarymanagement.formats.LoggerFormat
  with sbt.internal.librarymanagement.formats.UpdateOptionsFormat
  with sjsonnew.BasicJsonProtocol
  with sbt.librarymanagement.IvyPathsFormats
  with sbt.librarymanagement.ResolverFormats
  with sbt.librarymanagement.ModuleConfigurationFormats
  with sbt.librarymanagement.InlineIvyConfigurationFormats
  with sbt.librarymanagement.ExternalIvyConfigurationFormats
  with sbt.librarymanagement.IvyConfigurationFormats
object LibraryManagementCodec extends LibraryManagementCodec