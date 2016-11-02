package sbt.internal.librarymanagement.formats

import java.util.{ Map => JMap }
import scala.collection.JavaConverters._
import sjsonnew._

trait JavaMapFormat { self: BasicJsonProtocol =>
  implicit def JavaMapFormat[K: JsonFormat, V: JsonFormat]: JsonFormat[JMap[K, V]] =
    project(_.asScala.toMap, (m: Map[K, V]) => m.asJava)
}
