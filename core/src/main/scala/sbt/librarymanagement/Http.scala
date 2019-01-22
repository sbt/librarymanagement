package sbt.librarymanagement

import gigahorse._, support.okhttp.Gigahorse
import scala.concurrent.duration.DurationInt

object Http {
  lazy val http: HttpClient = Gigahorse.http(Gigahorse.config.withReadTimeout(60.minutes))
}
