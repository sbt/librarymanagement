import sbt._
import Keys._
import sbt.contraband.ContrabandPlugin.autoImport._

object Dependencies {
  val scala212 = "2.12.21"
  val scala213 = "2.13.18"

  def nightlyVersion: Option[String] =
    sys.env.get("BUILD_VERSION") orElse sys.props.get("sbt.build.version")

  private val ioVersion = nightlyVersion.getOrElse("1.13.0")
  private val utilVersion = nightlyVersion.getOrElse("1.13.0")

  private val sbtIO = "org.scala-sbt" %% "io" % ioVersion

  private val utilPosition = "org.scala-sbt" %% "util-position" % utilVersion
  private val utilLogging = "org.scala-sbt" %% "util-logging" % utilVersion
  private val utilCache = "org.scala-sbt" %% "util-cache" % utilVersion

  def getSbtModulePath(key: String, name: String) = {
    val localProps = new java.util.Properties()
    IO.load(localProps, file("project/local.properties"))
    val path = Option(localProps getProperty key) orElse (sys.props get key)
    path foreach (f => println(s"Using $name from $f"))
    path
  }

  lazy val sbtIoPath = getSbtModulePath("sbtio.path", "sbt/io")
  lazy val sbtUtilPath = getSbtModulePath("sbtutil.path", "sbt/util")

  def addSbtModule(p: Project, path: Option[String], projectName: String, m: ModuleID) =
    path match {
      case Some(f) => p dependsOn ProjectRef(file(f), projectName)
      case None    => p settings (libraryDependencies += m)
    }

  def addSbtIO(p: Project): Project = addSbtModule(p, sbtIoPath, "io", sbtIO)
  def addSbtUtilPosition(p: Project): Project =
    addSbtModule(p, sbtUtilPath, "utilPosition", utilPosition)
  def addSbtUtilLogging(p: Project): Project =
    addSbtModule(p, sbtUtilPath, "utilLogging", utilLogging)
  def addSbtUtilCache(p: Project): Project = addSbtModule(p, sbtUtilPath, "utilCache", utilCache)

  val launcherInterface = "org.scala-sbt" % "launcher-interface" % "1.6.2"
  val ivy = "org.scala-sbt.ivy" % "ivy" % "2.3.0-sbt-f686954b0021a5c3245766ced0cdaeca8ba2fd7a"

  val sbtV = "1.0"
  val scalaV = "2.12"

  val jsch = "com.github.mwiede" % "jsch" % "2.27.5" intransitive ()
  val scalaReflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }
  val scalaCompiler = Def.setting { "org.scala-lang" % "scala-compiler" % scalaVersion.value }
  val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "2.3.0"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.2.20"
  val scalaVerify = "com.eed3si9n.verify" %% "verify" % "1.0.0"
  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.20.0"
  val sjsonnew = Def.setting {
    "com.eed3si9n" %% "sjson-new-core" % contrabandSjsonNewVersion.value
  }
  val sjsonnewScalaJson = Def.setting {
    "com.eed3si9n" %% "sjson-new-scalajson" % contrabandSjsonNewVersion.value
  }
  val gigahorseApacheHttp = "com.eed3si9n" %% "gigahorse-apache-http" % "0.9.4"
}
