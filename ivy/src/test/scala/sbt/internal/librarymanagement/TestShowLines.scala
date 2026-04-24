package sbt
package internal
package librarymanagement

import sbt.util.ShowLines

object TestShowLines {
  implicit class RichShowLines[A: ShowLines](a: A) {
    def lines: Seq[String] =
      implicitly[ShowLines[A]].showLines(a)
  }
}
