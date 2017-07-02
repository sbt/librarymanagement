package sbt.librarymanagement

// Interface for library management

trait LibraryManagement {
  def buildModule(moduleId: ModuleID): ModuleDescriptor
  def buildModule(moduleId: ModuleID, scalaModuleInfo: Option[ScalaModuleInfo]): ModuleDescriptor
  def buildModule(
      moduleId: ModuleID,
      directDependencies: Vector[ModuleID],
      scalaModuleInfo: Option[ScalaModuleInfo]
  ): ModuleDescriptor
}

object LibraryManagement extends LibraryManagementFunctions
abstract class LibraryManagementFunctions {
  // lazy val specialArtifactTypes: Set[String] = Artifact.specialArtifactTypes
  // lazy val defaultArtifactTypeFilter: ArtifactTypeFilter =
  //   Artifact.defaultArtifactTypeFilter
}

trait ModuleDescriptor {
  def directDependenciesForWarning: Vector[ModuleID]
  def scalaModuleInfo: Option[ScalaModuleInfo]
}
