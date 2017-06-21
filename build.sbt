import Dependencies._
import Path._

// import com.typesafe.tools.mima.core._, ProblemFilters._

def baseVersion = "1.0.0"

def commonSettings: Seq[Setting[_]] = Seq(
  scalaVersion := scala212,
  // publishArtifact in packageDoc := false,
  resolvers += Resolver.typesafeIvyRepo("releases"),
  resolvers += Resolver.sonatypeRepo("snapshots"),
  resolvers += "bintray-sbt-maven-releases" at "https://dl.bintray.com/sbt/maven-releases/",
  // concurrentRestrictions in Global += Util.testExclusiveRestriction,
  testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-w", "1"),
  javacOptions in compile ++= Seq("-Xlint", "-Xlint:-serial"),
  crossScalaVersions := Seq(scala211, scala212),
  resolvers += Resolver.sonatypeRepo("public"),
  scalacOptions := {
    val old = scalacOptions.value
    scalaVersion.value match {
      case sv if sv.startsWith("2.10") => old diff List("-Xfuture", "-Ywarn-unused", "-Ywarn-unused-import")
      case sv if sv.startsWith("2.11") => old ++ List("-Ywarn-unused", "-Ywarn-unused-import")
      case _                           => old ++ List("-Ywarn-unused", "-Ywarn-unused-import", "-YdisableFlatCpCaching")
    }
  },
  // mimaPreviousArtifacts := Set(), // Some(organization.value %% moduleName.value % "1.0.0"),
  publishArtifact in Compile := true,
  publishArtifact in Test := false,
  parallelExecution in Test := false
)

lazy val lmRoot = (project in file("."))
  .aggregate(lm)
  .settings(
    inThisBuild(
      Seq(
        homepage := Some(url("https://github.com/sbt/librarymanagement")),
        description := "Library management module for sbt",
        scmInfo := Some(ScmInfo(
          url("https://github.com/sbt/librarymanagement"), "git@github.com:sbt/librarymanagement.git"
        )),
        bintrayPackage := "librarymanagement",
        scalafmtOnCompile := true,
        // scalafmtVersion 1.0.0-RC3 has regression
        scalafmtVersion := "0.6.8",
        git.baseVersion := baseVersion
      )),
    commonSettings,
    name := "LM Root",
    publish := {},
    publishLocal := {},
    publishArtifact in Compile := false,
    publishArtifact := false,
    customCommands
  )

lazy val lm = (project in file("librarymanagement"))
  .settings(
    commonSettings,
    name := "librarymanagement",
    libraryDependencies ++= Seq(
      ivy, jsch, scalaReflect.value, launcherInterface, gigahorseOkhttp, okhttpUrlconnection,
      sjsonnewScalaJson % Optional,
      "org.scalatest" %% "scalatest" % "3.0.1" % Test
    ),
    libraryDependencies ++= scalaXml.value,
    resourceGenerators in Compile += Def.task(
      Util.generateVersionFile(
        version.value, resourceManaged.value, streams.value, (compile in Compile).value
      )
    ).taskValue,
    // mimaBinaryIssueFilters ++= Seq(),
    contrabandFormatsForType in generateContrabands in Compile := DatatypeConfig.getFormats,
    // WORKAROUND sbt/sbt#2205 include managed sources in packageSrc
    mappings in (Compile, packageSrc) ++= {
      val srcs = (managedSources in Compile).value
      val sdirs = (managedSourceDirectories in Compile).value
      val base = baseDirectory.value
      (((srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)) toSeq)
    }
  )
  .configure(
    addSbtIO,
    addSbtUtilLogging,
    addSbtUtilCollection,
    addSbtUtilCompletion,
    addSbtUtilCache
  )
  .enablePlugins(ContrabandPlugin, JsonCodecPlugin)

def customCommands: Seq[Setting[_]] = Seq(
  commands += Command.command("release") { state =>
    // "clean" ::
    "so compile" ::
      "so publishSigned" ::
      "reload" ::
      state
  }
)
