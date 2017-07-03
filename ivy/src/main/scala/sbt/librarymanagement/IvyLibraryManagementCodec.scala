package sbt.librarymanagement

trait IvyLibraryManagementCodec
    extends sjsonnew.BasicJsonProtocol
    with LibraryManagementCodec
    with sbt.internal.librarymanagement.formats.GlobalLockFormat
    with sbt.internal.librarymanagement.formats.LoggerFormat
    with sbt.internal.librarymanagement.formats.UpdateOptionsFormat
    with IvyPathsFormats
    with ResolverFormats
    with ModuleConfigurationFormats
    with InlineIvyConfigurationFormats
    with ExternalIvyConfigurationFormats
    with IvyConfigurationFormats

object IvyLibraryManagementCodec extends IvyLibraryManagementCodec
