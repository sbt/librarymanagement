package sbt.internal.librarymanagement.formats

import sjsonnew._
import xsbti._

trait F1Format { self: BasicJsonProtocol =>
  implicit def F1Format[T, U]: JsonFormat[F1[T, U]] =
    project(MyCrazyReferences.referenced _, (ref: String) => MyCrazyReferences(ref, classOf[F1[T, U]]))
}
