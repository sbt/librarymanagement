package sbt
package internal
package librarymanagement

import sbt.librarymanagement._

object syntax {
  implicit def richUpdateReport(ur: UpdateReport): RichUpdateReport = new RichUpdateReport(ur)
}
