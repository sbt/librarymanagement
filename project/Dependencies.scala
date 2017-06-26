import sbt._
import Keys._

object Dependencies {
  val scala210 = "2.10.6"
  val scala211 = "2.11.11"
  val scala212 = "2.12.2"

  private val ioVersion = "1.0.0-M11"

  private val sbtIO = "org.scala-sbt" %% "io" % ioVersion

  def getSbtModulePath(key: String, name: String) = {
    val localProps = new java.util.Properties()
    IO.load(localProps, file("project/local.properties"))
    val path = Option(localProps getProperty key) orElse (sys.props get key)
    path foreach (f => println(s"Using $name from $f"))
    path
  }

  lazy val sbtIoPath = getSbtModulePath("sbtio.path", "sbt/io")

  def addSbtModule(p: Project, path: Option[String], projectName: String, m: ModuleID) =
    path match {
      case Some(f) => p dependsOn ProjectRef(file(f), projectName)
      case None    => p settings (libraryDependencies += m)
    }

  def addSbtIO(p: Project): Project = addSbtModule(p, sbtIoPath, "io", sbtIO)

  val launcherInterface = "org.scala-sbt" % "launcher-interface" % "1.0.0"

  val ivy = "org.scala-sbt.ivy" % "ivy" % "2.3.0-sbt-a3314352b638afbf0dca19f127e8263ed6f898bd"
  val jline = "jline" % "jline" % "2.14.4"
  val jsch = "com.jcraft" % "jsch" % "0.1.46" intransitive ()

  val scalaReflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }
  val scalaXml = scala211Module("scala-xml", "1.0.5")

  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4" % Test
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

  val sjsonnewVersion = "0.7.0"
  val sjsonnewScalaJson = "com.eed3si9n" %% "sjson-new-scalajson" % sjsonnewVersion

  def log4jVersion = "2.8.1"
  val log4jApi = "org.apache.logging.log4j" % "log4j-api" % log4jVersion
  val log4jCore = "org.apache.logging.log4j" % "log4j-core" % log4jVersion
  val log4jSlf4jImpl = "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion
  val disruptor = "com.lmax" % "disruptor" % "3.3.6"

  val gigahorseOkhttp = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.0"

  val okhttpUrlconnection = "com.squareup.okhttp3" % "okhttp-urlconnection" % "3.7.0"

  private def scala211Module(name: String, moduleVersion: String) =
    Def.setting {
      scalaVersion.value match {
        case sv if (sv startsWith "2.9.") || (sv startsWith "2.10.") => Nil
        case _                                                       => ("org.scala-lang.modules" %% name % moduleVersion) :: Nil
      }
    }
}
