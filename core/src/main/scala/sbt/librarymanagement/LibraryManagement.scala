package sbt.librarymanagement

// Interface for library management

trait LibraryManagement {
  def getModule(moduleId: ModuleID): ModuleDescriptor
}

trait ModuleDescriptor {
  def directDependenciesForWarning: Vector[ModuleID]
  def scalaModuleInfo: Option[ScalaModuleInfo]
}
