package sbt.librarymanagement

import sjsonnew.support.scalajson.unsafe.{ CompactPrinter, Converter, Parser }

import java.net.URI

object ScmInfoTest extends verify.BasicTestSuite {

  test("it should format itself into JSON") {
    import LibraryManagementCodec._
    val json = Converter.toJson(ScmInfo(new URI("/"), "connection", "devconnection")).get
    assert(CompactPrinter(json) == expectedJson)
  }

  test("it should thaw back from JSON") {
    import LibraryManagementCodec._
    val json = Parser.parseUnsafe(expectedJson)
    val m = Converter.fromJsonUnsafe[ScmInfo](json)
    assert(m == ScmInfo(new URI("/"), "connection", "devconnection"))
  }

  def expectedJson =
    """{"browseUrl":"/","connection":"connection","devConnection":"devconnection"}"""
}
