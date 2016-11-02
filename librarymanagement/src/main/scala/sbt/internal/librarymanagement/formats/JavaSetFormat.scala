package sbt.internal.librarymanagement.formats

import scala.collection.JavaConverters._
import java.util.{ Set => JSet }
import sjsonnew._

trait JavaSetFormat { self: BasicJsonProtocol =>
  implicit def JavaSetFormat[T: JsonFormat]: JsonFormat[JSet[T]] =
    project(_.asScala.toSet, (s: Set[T]) => s.asJava)
}
