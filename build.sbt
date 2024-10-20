import Dependencies._
import Path._
import com.typesafe.tools.mima.core._, ProblemFilters._

val _ = {
  // https://github.com/sbt/contraband/issues/122
  sys.props += ("line.separator" -> "\n")
}
Global / semanticdbEnabled := !(Global / insideCI).value
Global / semanticdbVersion := "4.9.9"
ThisBuild / version := {
  val old = (ThisBuild / version).value
  nightlyVersion match {
    case Some(v) => v
    case _ =>
      if ((ThisBuild / isSnapshot).value) "1.7.2-SNAPSHOT"
      else old
  }
}
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / organization := "org.scala-sbt"
ThisBuild / homepage := Some(url("https://github.com/sbt/librarymanagement"))
ThisBuild / description := "Library management module for sbt"
ThisBuild / scmInfo := {
  val slug = "sbt/librarymanagement"
  Some(ScmInfo(url(s"https://github.com/$slug"), s"git@github.com:$slug.git"))
}
ThisBuild / licenses := List(("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")))
ThisBuild / developers := List(
  Developer("harrah", "Mark Harrah", "@harrah", url("https://github.com/harrah")),
  Developer("eed3si9n", "Eugene Yokota", "@eed3si9n", url("http://eed3si9n.com/")),
  Developer("dwijnand", "Dale Wijnand", "@dwijnand", url("https://github.com/dwijnand")),
)
ThisBuild / pomIncludeRepository := (_ => false) // drop repos other than Maven Central from POM
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

ThisBuild / Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat

def commonSettings: Seq[Setting[_]] = Def.settings(
  scalaVersion := scala212,
  // publishArtifact in packageDoc := false,
  resolvers += Resolver.typesafeIvyRepo("releases"),
  resolvers ++= Resolver.sonatypeOssRepos("snapshots"),
  resolvers += Resolver.sbtPluginRepo("releases"),
  testFrameworks += new TestFramework("verify.runner.Framework"),
  // concurrentRestrictions in Global += Util.testExclusiveRestriction,
  testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-w", "1"),
  compile / javacOptions ++= Seq("-Xlint", "-Xlint:-serial"),
  crossScalaVersions := Seq(scala212, scala213),
  resolvers ++= Resolver.sonatypeOssRepos("public"),
  scalacOptions := {
    val old = scalacOptions.value
    scalaVersion.value match {
      case sv if sv.startsWith("2.12") =>
        old ++ List(
          "-Ywarn-unused",
          "-Ywarn-unused-import",
          "-Ywarn-unused:-nowarn",
        )
      case _ => old
    }
  },
  inCompileAndTest(
    (console / scalacOptions) --= Vector("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint")
  ),
  Compile / publishArtifact := true,
  Test / publishArtifact := false,
  Test / parallelExecution := false
)

val mimaSettings = Def settings (
  mimaPreviousArtifacts := Set(
    "1.0.0",
    "1.0.1",
    "1.0.2",
    "1.0.3",
    "1.0.4",
    "1.1.0",
    "1.1.1",
    "1.1.2",
    "1.1.3",
    "1.1.4",
    "1.2.0",
    "1.3.0",
    "1.4.0",
    "1.5.0",
    "1.6.0",
  ) map (version =>
    organization.value %% moduleName.value % version
      cross (if (crossPaths.value) CrossVersion.binary else CrossVersion.disabled)
  ),
)

lazy val lmRoot = (project in file("."))
  .aggregate(lmCore, lmIvy)
  .settings(
    commonSettings,
    name := "LM Root",
    publish := {},
    publishLocal := {},
    Compile / publishArtifact := false,
    publishArtifact := false,
    mimaPreviousArtifacts := Set.empty,
    customCommands,
  )

lazy val lmCore = (project in file("core"))
  .enablePlugins(ContrabandPlugin, JsonCodecPlugin)
  .settings(
    commonSettings,
    name := "librarymanagement-core",
    libraryDependencies ++= Seq(
      jsch,
      scalaReflect.value,
      scalaCompiler.value,
      launcherInterface,
      gigahorseApacheHttp,
      sjsonnewScalaJson.value % Optional,
      scalaTest % Test,
      scalaCheck % Test,
      scalaVerify % Test,
    ),
    libraryDependencies += scalaXml,
    Compile / resourceGenerators += Def
      .task(
        Util.generateVersionFile(
          version.value,
          resourceManaged.value,
          streams.value,
          (Compile / compile).value
        )
      )
      .taskValue,
    Compile / scalacOptions ++= (scalaVersion.value match {
      case v if v.startsWith("2.12.") => List("-Ywarn-unused:-locals,-explicits,-privates")
      case _                          => List()
    }),
    Compile / managedSourceDirectories +=
      baseDirectory.value / "src" / "main" / "contraband-scala",
    Compile / generateContrabands / sourceManaged := baseDirectory.value / "src" / "main" / "contraband-scala",
    Compile / generateContrabands / contrabandFormatsForType := DatatypeConfig.getFormats,
    // WORKAROUND sbt/sbt#2205 include managed sources in packageSrc
    Compile / packageSrc / mappings ++= {
      val srcs = (Compile / managedSources).value
      val sdirs = (Compile / managedSourceDirectories).value
      val base = baseDirectory.value
      (((srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)) toSeq)
    },
    mimaSettings,
    mimaBinaryIssueFilters ++= Seq(
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.EvictionWarningOptions.this"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.EvictionWarningOptions.copy"),
      exclude[IncompatibleResultTypeProblem](
        "sbt.librarymanagement.EvictionWarningOptions.copy$default$7"
      ),
      // internal class moved
      exclude[MissingClassProblem]("sbt.internal.librarymanagement.InlineConfigurationFunctions"),
      // dropped internal class parent (InlineConfigurationFunctions)
      exclude[MissingTypesProblem]("sbt.librarymanagement.ModuleDescriptorConfiguration$"),
      // Configuration's copy method was never meant to be public
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Configuration.copy"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Configuration.copy$default$*"),
      // the data type copy methods were never meant to be public
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.copy"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.copy$default$*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleReportExtra.copy"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleReportExtra.copy$default$*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactTypeFilterExtra.copy"),
      exclude[DirectMissingMethodProblem](
        "sbt.librarymanagement.ArtifactTypeFilterExtra.copy$default$*"
      ),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleIDExtra.copy"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleIDExtra.copy$default$*"),
      // these abstract classes are private[librarymanagement] so it's fine if they have more methods
      exclude[ReversedMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.*"),
      exclude[ReversedMissingMethodProblem]("sbt.librarymanagement.ModuleReportExtra.*"),
      exclude[ReversedMissingMethodProblem]("sbt.librarymanagement.ArtifactTypeFilterExtra.*"),
      exclude[ReversedMissingMethodProblem]("sbt.librarymanagement.ModuleIDExtra.*"),
      // these abstract classes are private[librarymanagement] so they can lose these abstract methods
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.type"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.url"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.checksum"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.name"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.configurations"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.classifier"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactExtra.extension"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactTypeFilterExtra.types"),
      // contraband issue
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ConfigurationReportLite.copy*"
      ),
      exclude[DirectMissingMethodProblem]("sbt.internal.librarymanagement.UpdateReportLite.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Artifact.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ArtifactTypeFilter.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Binary.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Caller.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ChainedResolver.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Checksum.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ConfigRef.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ConfigurationReport.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ConflictManager.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Constant.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Developer.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Disabled.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.FileConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.FileRepository.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Full.copy*"),
      exclude[DirectMissingMethodProblem](
        "sbt.librarymanagement.GetClassifiersConfiguration.copy*"
      ),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.GetClassifiersModule.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.InclExclRule.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.IvyFileConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.KeyFileAuthentication.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.MakePomConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.MavenCache.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.MavenRepo.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleConfiguration.copy*"),
      exclude[DirectMissingMethodProblem](
        "sbt.librarymanagement.ModuleDescriptorConfiguration.copy*"
      ),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleID.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleInfo.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ModuleReport.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.OrganizationArtifactReport.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.PasswordAuthentication.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Patch.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.Patterns.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.PomConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.PublishConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.RetrieveConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ScalaModuleInfo.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ScmInfo.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.SftpRepository.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.SshConnection.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.SshRepository.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.UpdateConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.UpdateReport.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.UpdateStats.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.URLRepository.copy*"),
      // private[sbt]
      exclude[DirectMissingMethodProblem](
        "sbt.librarymanagement.ResolverFunctions.useSecureResolvers"
      ),
      exclude[ReversedMissingMethodProblem](
        "sbt.librarymanagement.MavenRepository.allowInsecureProtocol"
      ),
      exclude[IncompatibleResultTypeProblem](
        "sbt.librarymanagement.ResolverFunctions.validateURLRepository"
      ),
      exclude[IncompatibleResultTypeProblem](
        "sbt.librarymanagement.ResolverFunctions.validateMavenRepo"
      ),
      exclude[IncompatibleResultTypeProblem](
        "sbt.librarymanagement.ResolverFunctions.validateArtifact"
      ),
      exclude[IncompatibleResultTypeProblem]("sbt.librarymanagement.*.validateProtocol"),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.cross.CrossVersionUtil.TransitionDottyVersion"
      ),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ScalaArtifacts.dottyID"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ScalaArtifacts.DottyIDPrefix"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ScalaArtifacts.toolDependencies*"),
    ),
  )
  .configure(addSbtIO, addSbtUtilLogging, addSbtUtilPosition, addSbtUtilCache)

lazy val lmIvy = (project in file("ivy"))
  .enablePlugins(ContrabandPlugin, JsonCodecPlugin)
  .dependsOn(lmCore)
  .settings(
    commonSettings,
    name := "librarymanagement-ivy",
    libraryDependencies ++= Seq(
      ivy,
      scalaTest % Test,
      scalaCheck % Test,
      scalaVerify % Test,
    ),
    Compile / managedSourceDirectories +=
      baseDirectory.value / "src" / "main" / "contraband-scala",
    Compile / generateContrabands / sourceManaged := baseDirectory.value / "src" / "main" / "contraband-scala",
    Compile / generateContrabands / contrabandFormatsForType := DatatypeConfig.getFormats,
    Compile / console / scalacOptions --=
      Vector("-Ywarn-unused-import", "-Ywarn-unused", "-Xlint"),
    mimaSettings,
    mimaBinaryIssueFilters ++= Seq(
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler#SbtUrlInfo.this"
      ),
      exclude[IncompatibleMethTypeProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler#SbtUrlInfo.this"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler.checkStatusCode"
      ),
      // sbt.internal methods that changed type signatures and aren't used elsewhere in production code
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.IvySbt#ParallelCachedResolutionResolveEngine.mergeErrors"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.IvySbt.cleanCachedResolutionCache"
      ),
      exclude[DirectMissingMethodProblem]("sbt.internal.librarymanagement.IvyRetrieve.artifacts"),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.IvyScalaUtil.checkModule"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.CachedResolutionResolveEngine.mergeErrors"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.CachedResolutionResolveCache.buildArtificialModuleDescriptor"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.CachedResolutionResolveCache.buildArtificialModuleDescriptors"
      ),
      exclude[ReversedMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.CachedResolutionResolveEngine.mergeErrors"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler#SbtUrlInfo.this"
      ),
      exclude[IncompatibleMethTypeProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler#SbtUrlInfo.this"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler.checkStatusCode"
      ),
      // contraband issue
      exclude[DirectMissingMethodProblem](
        "sbt.librarymanagement.ivy.ExternalIvyConfiguration.copy*"
      ),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ivy.InlineIvyConfiguration.copy*"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ivy.IvyPaths.copy*"),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler.urlFactory"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler.http"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler.open"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler.this"
      ),
      exclude[DirectMissingMethodProblem](
        "sbt.internal.librarymanagement.CustomPomParser.versionRangeFlag"
      ),
      exclude[MissingClassProblem]("sbt.internal.librarymanagement.FixedParser*"),
      exclude[MissingClassProblem]("sbt.internal.librarymanagement.ivyint.GigahorseUrlHandler*"),
      exclude[MissingClassProblem]("sbt.internal.librarymanagement.JavaNetAuthenticator"),
      exclude[MissingClassProblem]("sbt.internal.librarymanagement.CustomHttp*"),
      exclude[DirectMissingMethodProblem]("sbt.internal.librarymanagement.IvySbt.http"),
      exclude[DirectMissingMethodProblem]("sbt.internal.librarymanagement.IvySbt.this"),
      exclude[DirectMissingMethodProblem]("sbt.librarymanagement.ivy.IvyPublisher.apply"),
      exclude[DirectMissingMethodProblem](
        "sbt.librarymanagement.ivy.IvyDependencyResolution.apply"
      ),
    ),
  )

// lazy val lmScriptedTest = (project in file("scripted-test"))
//   .enablePlugins(SbtPlugin)
//   .settings(
//     commonSettings,
//     skip in publish := true,
//     name := "scripted-test",
//     scriptedLaunchOpts := {
//       scriptedLaunchOpts.value ++
//         Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
//     },
//     scriptedBufferLog := false
//   )
//   .enablePlugins(SbtScriptedIT)

// we are updating the nightly process, so we are commenting this out for now
// addCommandAlias("scriptedIvy", Seq(
//   "lmCore/publishLocal",
//   "lmIvy/publishLocal",
//   "lmScriptedTest/clean",
//   """set ThisBuild / scriptedTestLMImpl := "ivy"""",
//   """set ThisBuild / scriptedLaunchOpts += "-Ddependency.resolution=ivy" """,
//   "lmScriptedTest/scripted").mkString(";",";",""))

def customCommands: Seq[Setting[_]] = Seq(
  commands += Command.command("release") { state =>
    // "clean" ::
    "+compile" ::
      "+publishSigned" ::
      "reload" ::
      state
  }
)

def inCompileAndTest(ss: SettingsDefinition*): Seq[Setting[_]] =
  Seq(Compile, Test) flatMap (inConfig(_)(Def.settings(ss: _*)))
