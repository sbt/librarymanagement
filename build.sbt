import Dependencies._

def internalPath   = file("internal")
def baseVersion: String = "0.1.0-M3"

def commonSettings: Seq[Setting[_]] = Seq(
  scalaVersion := "2.10.5",
  // publishArtifact in packageDoc := false,
  resolvers += Resolver.typesafeIvyRepo("releases"),
  resolvers += Resolver.sonatypeRepo("snapshots"),
  resolvers += Resolver.bintrayRepo("sbt", "maven-releases"),
  // concurrentRestrictions in Global += Util.testExclusiveRestriction,
  testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-w", "1"),
  javacOptions in compile ++= Seq("-target", "6", "-source", "6", "-Xlint", "-Xlint:-serial"),
  incOptions := incOptions.value.withNameHashing(true),
  crossScalaVersions := Seq(scala210, scala211),
  bintrayPackage := (bintrayPackage in ThisBuild).value,
  bintrayRepository := (bintrayRepository in ThisBuild).value,
  resolvers += Resolver.sonatypeRepo("public"),
  publishArtifact in Compile := true,
  publishArtifact in Test := true
)

lazy val root = (project in file(".")).
  aggregate(lm).
  settings(
    inThisBuild(Seq(
      git.baseVersion := baseVersion,
      homepage := Some(url("https://github.com/sbt/librarymanagement")),
      description := "Library management module for sbt",
      scmInfo := Some(ScmInfo(url("https://github.com/sbt/librarymanagement"), "git@github.com:sbt/librarymanagement.git")),
      bintrayPackage := "librarymanagement"
    )),
    commonSettings,
    name := "LM Root",
    publish := {},
    publishLocal := {},
    publishArtifact in Compile := false,
    publishArtifact in Test := false,
    publishArtifact := false
  )

lazy val lm = (project in file("librarymanagement")).
  settings(
    commonSettings,
    libraryDependencies ++= Seq(
      utilLogging, (utilLogging % Test).classifier("tests"),
      sbtIO, (sbtIO % Test).classifier("tests"),
      utilTesting % Test,
      utilCollection, ivy, jsch, sbtSerialization, scalaReflect.value, launcherInterface),
    resourceGenerators in Compile <+= (version, resourceManaged, streams, compile in Compile) map Util.generateVersionFile,
    name := "librarymanagement"
  )
