package sbt.librarymanagement.coursier

import sbt.librarymanagement.Configurations.Component
import sbt.librarymanagement.Resolver.{
  DefaultMavenRepository,
  JCenterRepository,
  JavaNet2Repository
}
import sbt.librarymanagement.syntax._
import sbt.librarymanagement.{ Resolver, UnresolvedWarningConfiguration, UpdateConfiguration }

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
    "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
  ).map(_.withIsTransitive(false))

  "Coursier dependency resolution" should "resolve very simple module" in {
    val coursierModule = module(stubModule, dependencies, Some("2.12.4"))
    val resolution =
      lmEngine.update(coursierModule, UpdateConfiguration(), UnresolvedWarningConfiguration(), log)

    resolution should be('right)
    val r = resolution.toOption.get
    r.configurations.map(_.configuration) should have size 11

    val compileConfig = r.configurations.find(_.configuration == Compile.toConfigRef).get
    compileConfig.modules should have size 1

    val runtimeConfig = r.configurations.find(_.configuration == Runtime.toConfigRef).get
    runtimeConfig.modules should have size 1

    val testConfig = r.configurations.find(_.configuration == Test.toConfigRef).get
    testConfig.modules should have size 1
  }

  it should "resolve compiler bridge" in {
    val dependencies =
      Vector(("org.scala-sbt" % "compiler-interface" % "1.0.4" % "component").sources())
    val coursierModule = module(stubModule, dependencies, Some("2.12.4"))
    val resolution =
      lmEngine.update(coursierModule, UpdateConfiguration(), UnresolvedWarningConfiguration(), log)

    val r = resolution.toOption.get

    val componentConfig = r.configurations.find(_.configuration == Component.toConfigRef).get
    componentConfig.modules should have size 1
    componentConfig.modules.head.artifacts should have size 1
    componentConfig.modules.head.artifacts.head._1.classifier should contain("sources")
  }
}
