package sbt

import sbt.librarymanagement.{ Resolver, UnresolvedWarningConfiguration, UpdateConfiguration }
import sbt.librarymanagement.Resolver.{
  DefaultMavenRepository,
  JCenterRepository,
  JavaNet2Repository
}
import sbt.librarymanagement.syntax._

class ResolutionSpec extends BaseCoursierSpecification {
  override final val resolvers = Vector(
    DefaultMavenRepository,
    JavaNet2Repository,
    JCenterRepository,
    Resolver.sbtPluginRepo("releases")
  )

  private final val stubModule = "com.example" % "foo" % "0.1.0" % "compile"

  val pluginAttributes = Map("scalaVersion" -> "2.10")

  private final val dependencies = Vector(
    "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.2" % "compile",
    "org.scalatest" %% "scalatest_2.12" % "3.0.4" % "test"
  ).map(_.withIsTransitive(false))

  "Coursier dependency resolution" should "resolve very simple module" in {
    val coursierModule = module(stubModule, dependencies, Some("2.12.4"))
    val resolution =
      lmEngine.update(coursierModule, UpdateConfiguration(), UnresolvedWarningConfiguration(), log)

    resolution should be('right)
    val r = resolution.toOption.get
    r.configurations.map(_.configuration) should contain only (Compile.toConfigRef, Test.toConfigRef, Runtime.toConfigRef)
    val runtimeConfig = r.configurations.find(_.configuration == Runtime.toConfigRef).get
    runtimeConfig.modules should have size 4
    val testConfig = r.configurations.find(_.configuration == Test.toConfigRef).get
    runtimeConfig.modules should have size 1
  }
}
