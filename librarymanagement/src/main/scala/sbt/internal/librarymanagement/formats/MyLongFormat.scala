package sbt.internal.librarymanagement.formats

import sjsonnew._

trait MyLongFormat { self: BasicJsonProtocol =>
  implicit lazy val MyJavaLongFormat: JsonFormat[java.lang.Long] =
    project(_.toString, (s: String) => java.lang.Long.parseLong(s))
}
