package sbt.librarymanagement

import java.io.File
import scala.xml.{ Node => XNode, NodeSeq }

// final class DeliverConfiguration(
//     val deliverIvyPattern: String,
//     val status: String,
//     val configurations: Option[Vector[Configuration]],
//     val logging: UpdateLogging
// )

final class MakePomConfiguration(
    val file: File,
    val moduleInfo: ModuleInfo,
    val configurations: Option[Vector[Configuration]] = None,
    val extra: NodeSeq = NodeSeq.Empty,
    val process: XNode => XNode = n => n,
    val filterRepositories: MavenRepository => Boolean = _ => true,
    val allRepositories: Boolean,
    val includeTypes: Set[String] = Set(Artifact.DefaultType, Artifact.PomType)
)
