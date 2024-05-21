package sbt.librarymanagement

import sjsonnew.support.scalajson.unsafe.{ CompactPrinter, Converter, Parser }

import java.net.URI

object DeveloperTest extends verify.BasicTestSuite {

  test("it should format itself into JSON") {
    import LibraryManagementCodec._
    val json = Converter.toJson(Developer("id", "joao", "email", new URI("/"))).get
    assert(CompactPrinter(json) == expectedJson)
  }

  test("it should thaw back from JSON") {
    import LibraryManagementCodec._
    val json = Parser.parseUnsafe(expectedJson)
    val m = Converter.fromJsonUnsafe[Developer](json)
    assert(m == Developer("id", "joao", "email", new URI("/")))
  }

  def expectedJson =
    """{"id":"id","name":"joao","email":"email","url":"/"}"""
}
