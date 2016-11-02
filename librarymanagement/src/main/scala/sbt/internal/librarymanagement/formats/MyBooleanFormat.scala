package sbt.internal.librarymanagement.formats

import sjsonnew._

trait MyBooleanFormat { self: BasicJsonProtocol =>
  implicit lazy val MyJavaBooleanFormat: JsonFormat[java.lang.Boolean] =
    project(_.toString, java.lang.Boolean.parseBoolean _)
}
