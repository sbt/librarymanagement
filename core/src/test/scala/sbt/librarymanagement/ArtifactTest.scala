package sbt.librarymanagement

import sjsonnew.support.scalajson.unsafe.{ CompactPrinter, Converter, Parser }

import java.net.{ URI, URL }

object ArtifactTest extends verify.BasicTestSuite {

  test("it should format itself into JSON") {
    import LibraryManagementCodec._
    val json = Converter.toJson(Artifact("scala-library").withUri(Some(new URI("http:/")))).get
    assert(CompactPrinter(json) == expectedJson)
  }

  test("it should thaw back from JSON") {
    import LibraryManagementCodec._
    val json = Parser.parseUnsafe(expectedJson)
    val m = Converter.fromJsonUnsafe[Artifact](json)
    assert(m == Artifact("scala-library").withUri(Some(new URI("http:/"))))
  }

  test("it should be backwards compatible") {
    assert(
      Artifact(
        "scala-library",
        "jar",
        "jar",
        None,
        Vector.empty,
        Some(new URI("http:/")),
        Map.empty,
        None,
        false
      ) ==
        Artifact(
          "scala-library",
          "jar",
          "jar",
          None,
          Vector.empty,
          Some(new URL("http:/")),
          Map.empty,
          None,
          false
        )
    )
  }

  def expectedJson =
    """{"name":"scala-library","type":"jar","extension":"jar","configurations":[],"url":"http:/","extraAttributes":{},"allowInsecureProtocol":false}"""
}
