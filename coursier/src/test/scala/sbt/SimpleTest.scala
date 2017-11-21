package sbt

import sbt.librarymanagement.{ Resolver, UnresolvedWarningConfiguration, UpdateConfiguration }
import sbt.librarymanagement.Resolver.{
  DefaultMavenRepository,
  JCenterRepository,
  JavaNet2Repository
}
import sbt.librarymanagement.syntax._

class SimpleTest extends BaseCoursierSpecification {
  override final val resolvers = Vector(
    DefaultMavenRepository,
    JavaNet2Repository,
    JCenterRepository,
    Resolver.sbtPluginRepo("releases")
  )

  private final val stubModule = "com.example" % "foo" % "0.1.0" % "compile"

  val pluginAttributes = Map("scalaVersion" -> "2.10")

  private final val dependencies = Vector(
    "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.2"
  ).map(_.withIsTransitive(false))

  "Coursier dependency resolution" should "resolve very simple module" in {
    val coursierModule = module(stubModule, dependencies, Some("2.12.4"))
    val normalResolution =
      lmEngine.update(coursierModule, UpdateConfiguration(), UnresolvedWarningConfiguration(), log)

    normalResolution should be('right)
  }
}
