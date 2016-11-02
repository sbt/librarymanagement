package sbt.internal.librarymanagement.formats

import sjsonnew._
import xsbti._
import sbt.util.InterfaceUtil._

trait T2Format { self: BasicJsonProtocol =>
  implicit def T2Format[A1: JsonFormat, A2: JsonFormat]: JsonFormat[T2[A1, A2]] =
    project((t: T2[A1, A2]) => (t.get1, t.get2), t2 _)
}
