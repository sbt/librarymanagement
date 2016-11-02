package sbt.internal.librarymanagement.formats

import sjsonnew._

trait MyIntegerFormat { self: BasicJsonProtocol =>
  implicit lazy val MyJavaIntegerFormat: JsonFormat[java.lang.Integer] =
    project(_.toString, (s: String) => java.lang.Integer.parseInt(s))
}
