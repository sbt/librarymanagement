package sbt.internal.librarymanagement

import sbt.io.IO
import sbt.io.syntax._
import java.io.File
import cross.CrossVersionUtil
import sbt.util.Logger
import sbt.internal.util.ConsoleLogger
import sbt.librarymanagement._
import ivyint.SbtChainResolver
import Configurations._

import sbt.internal.util.FileBasedStore

import sjsonnew.IsoString
import sjsonnew.support.scalajson.unsafe.{ CompactPrinter, Converter }

import scala.json.ast.unsafe.JValue

trait BaseIvySpecification extends UnitSpec {
  def currentBase: File = new File(".")
  def currentTarget: File = currentBase / "target" / "ivyhome"
  def currentManaged: File = currentBase / "target" / "lib_managed"
  def currentDependency: File = currentBase / "target" / "dependency"
  def defaultModuleId: ModuleID = ModuleID("com.example", "foo", "0.1.0").withConfigurations(Some("compile"))

  implicit val isoString: IsoString[JValue] = IsoString.iso(CompactPrinter.apply, FixedParser.parseUnsafe)
  val fileToStore = (f: File) => new FileBasedStore(f, Converter)
  lazy val log = ConsoleLogger()

  def configurations = Vector(Compile, Test, Runtime)
  def module(moduleId: ModuleID, deps: Vector[ModuleID], scalaFullVersion: Option[String],
    uo: UpdateOptions = UpdateOptions(), overrideScalaVersion: Boolean = true): IvySbt#Module = {
    val ivyScala = scalaFullVersion map { fv =>
      new IvyScala(
        scalaFullVersion = fv,
        scalaBinaryVersion = CrossVersionUtil.binaryScalaVersion(fv),
        configurations = Vector.empty,
        checkExplicit = true,
        filterImplicit = false,
        overrideScalaVersion = overrideScalaVersion
      )
    }

    val moduleSetting: ModuleSettings = InlineConfiguration(
      false,
      ivyScala,
      module = moduleId,
      moduleInfo = ModuleInfo("foo"),
      dependencies = deps
    ).withConfigurations(configurations)
    val ivySbt = new IvySbt(mkIvyConfiguration(uo), fileToStore)
    new ivySbt.Module(moduleSetting)
  }

  def resolvers: Vector[Resolver] = Vector(DefaultMavenRepository)

  def chainResolver = ChainedResolver("sbt-chain", resolvers)

  def mkIvyConfiguration(uo: UpdateOptions): IvyConfiguration = {
    val paths = new IvyPaths(currentBase, Some(currentTarget))
    val other = Vector.empty
    val moduleConfs = Vector(ModuleConfiguration("*", chainResolver))
    val off = false
    val check = Vector.empty
    val resCacheDir = currentTarget / "resolution-cache"
    new InlineIvyConfiguration(paths, resolvers, other, moduleConfs, off, None, check, Some(resCacheDir), uo, log)
  }

  def makeUpdateConfiguration: UpdateConfiguration = {
    val retrieveConfig = new RetrieveConfiguration(currentManaged, Resolver.defaultRetrievePattern).withSync(false)
    new UpdateConfiguration(Some(retrieveConfig), false, UpdateLogging.Full, ArtifactTypeFilter.forbid(Set("src", "doc")))
  }

  def ivyUpdateEither(module: IvySbt#Module): Either[UnresolvedWarning, UpdateReport] = {
    // IO.delete(currentTarget)
    val config = makeUpdateConfiguration
    IvyActions.updateEither(module, config, UnresolvedWarningConfiguration(), LogicalClock.unknown, Some(currentDependency), log)
  }

  def cleanIvyCache(): Unit = IO.delete(currentTarget / "cache")

  def cleanCachedResolutionCache(module: IvySbt#Module): Unit = IvyActions.cleanCachedResolutionCache(module, log)

  def ivyUpdate(module: IvySbt#Module) =
    ivyUpdateEither(module) match {
      case Right(r) => r
      case Left(w) =>
        throw w.resolveException
    }

  def mkPublishConfiguration(resolver: Resolver, artifacts: Map[Artifact, File]): PublishConfiguration = {
    new PublishConfiguration(
      ivyFile = None,
      resolverName = resolver.name,
      artifacts = artifacts,
      checksums = Vector.empty,
      logging = UpdateLogging.Full,
      overwrite = true
    )
  }

  def ivyPublish(module: IvySbt#Module, config: PublishConfiguration) = {
    IvyActions.publish(module, config, log)
  }
}
