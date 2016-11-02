package sbt.internal.librarymanagement.formats

import sjsonnew._
import xsbti._
import sbt.util.InterfaceUtil._

trait MaybeFormat { self: BasicJsonProtocol =>
  implicit def MaybeFormat[T: JsonFormat]: JsonFormat[Maybe[T]] =
    project(
      (in: Maybe[T]) => if (in.isDefined) Option(in.get) else None,
      (in: Option[T]) => if (in == null) ??? else o2m(in)
    )
}
