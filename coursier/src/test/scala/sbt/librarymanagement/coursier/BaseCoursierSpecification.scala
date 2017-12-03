package sbt.librarymanagement.coursier

import java.io.File

import sbt.internal.librarymanagement.cross.CrossVersionUtil
import sbt.internal.util.ConsoleLogger
import sbt.io.syntax._
import sbt.librarymanagement.Configurations._
import sbt.librarymanagement._

trait BaseCoursierSpecification extends UnitSpec {
  def currentBase: File = new File(".")
  def currentTarget: File = currentBase / "target" / "ivyhome"
  def currentManaged: File = currentBase / "target" / "lib_managed"
  def currentDependency: File = currentBase / "target" / "dependency"
  def defaultModuleId: ModuleID =
    ModuleID("com.example", "foo", "0.1.0").withConfigurations(Some("compile"))

  lazy val log = ConsoleLogger()
  val lmEngine = CoursierDependencyResolution()

  def configurations = Vector(Compile, Test, Runtime)
  def module(moduleId: ModuleID,
             deps: Vector[ModuleID],
             scalaFullVersion: Option[String],
             overrideScalaVersion: Boolean = true): ModuleDescriptor = {
    val scalaModuleInfo = scalaFullVersion map { fv =>
      ScalaModuleInfo(
        scalaFullVersion = fv,
        scalaBinaryVersion = CrossVersionUtil.binaryScalaVersion(fv),
        configurations = configurations,
        checkExplicit = true,
        filterImplicit = false,
        overrideScalaVersion = overrideScalaVersion
      )
    }

    val moduleSetting = ModuleDescriptorConfiguration(moduleId, ModuleInfo("foo"))
      .withDependencies(deps)
      .withConfigurations(configurations)
      .withScalaModuleInfo(scalaModuleInfo)
    lmEngine.moduleDescriptor(moduleSetting)
  }

  def resolvers: Vector[Resolver] = Vector(Resolver.mavenCentral)

  def chainResolver = ChainedResolver("sbt-chain", resolvers)

}
