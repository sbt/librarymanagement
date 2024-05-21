package sbt.librarymanagement

import sjsonnew.support.scalajson.unsafe.{ CompactPrinter, Converter, Parser }

import java.net.URI

object ArtifactTest extends verify.BasicTestSuite {

  test("it should format itself into JSON") {
    import LibraryManagementCodec._
    val json = Converter.toJson(Artifact("scala-library").withUri(Some(new URI("")))).get
    assert(CompactPrinter(json) == expectedJson)
  }

  test("it should thaw back from JSON") {
    import LibraryManagementCodec._
    val json = Parser.parseUnsafe(expectedJson)
    val m = Converter.fromJsonUnsafe[Artifact](json)
    assert(m == Artifact("scala-library").withUri(Some(new URI(""))))
  }

  def expectedJson =
    """{"name":"scala-library","type":"jar","extension":"jar","configurations":[],"url":"","extraAttributes":{},"allowInsecureProtocol":false}"""
}
