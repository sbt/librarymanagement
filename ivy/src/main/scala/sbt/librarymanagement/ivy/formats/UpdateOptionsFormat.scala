package sbt.librarymanagement.ivy
package formats

import sjsonnew._
import sbt.librarymanagement._

trait UpdateOptionsFormat { self: BasicJsonProtocol with ModuleIDFormats with ResolverFormats =>
  /* This is necessary to serialize/deserialize `directResolvers`. */
  private implicit val moduleIdJsonKeyFormat: sjsonnew.JsonKeyFormat[ModuleID] = {
    new sjsonnew.JsonKeyFormat[ModuleID] {
      import sjsonnew.support.scalajson.unsafe._
      val moduleIdFormat: JsonFormat[ModuleID] = implicitly[JsonFormat[ModuleID]]
      def write(key: ModuleID): String =
        CompactPrinter(Converter.toJsonUnsafe(key)(moduleIdFormat))
      def read(key: String): ModuleID =
        Converter.fromJsonUnsafe[ModuleID](Parser.parseUnsafe(key))(moduleIdFormat)
    }
  }

  implicit lazy val UpdateOptionsFormat: JsonFormat[UpdateOptions] =
    projectFormat(
      (uo: UpdateOptions) =>
        (
          uo.circularDependencyLevel.name,
          uo.interProjectFirst,
          // This is for compatibility with old latestSnapshots which had opposite boolean value.
          // Safe to remove negation for sbt 2.0.x?
          !uo.cachedSnapshots,
          uo.cachedResolution,
          uo.gigahorse,
          uo.moduleResolvers
        ),
      (xs: (String, Boolean, Boolean, Boolean, Boolean, Map[ModuleID, Resolver])) =>
        new UpdateOptions(
          levels(xs._1),
          xs._2,
          // This is for compatibility with old latestSnapshots which had opposite boolean value.
          // Safe to remove negation for sbt 2.0.x?
          !xs._3,
          xs._4,
          xs._5,
          PartialFunction.empty,
          xs._6
        )
    )

  private val levels: Map[String, CircularDependencyLevel] = Map(
    "warn" -> CircularDependencyLevel.Warn,
    "ignore" -> CircularDependencyLevel.Ignore,
    "error" -> CircularDependencyLevel.Error
  )
}
