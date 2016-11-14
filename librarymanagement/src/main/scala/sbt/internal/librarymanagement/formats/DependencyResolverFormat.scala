package sbt.internal.librarymanagement.formats

import sjsonnew._
import org.apache.ivy.plugins.resolver.DependencyResolver

trait DependencyResolverFormat { self: BasicJsonProtocol =>
  implicit lazy val DependencyResolverFormat: JsonFormat[DependencyResolver] =
    project(
      MyCrazyReferences.referenced _,
      (ref: String) => MyCrazyReferences(ref, classOf[DependencyResolver])
    )
}
