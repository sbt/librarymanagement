/**
 * This code is generated using [[https://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class Artifact private (
  val name: String,
  val `type`: String,
  val extension: String,
  val classifier: Option[String],
  val configurations: Vector[sbt.librarymanagement.ConfigRef],
  val uri: Option[java.net.URI],
  val extraAttributes: Map[String, String],
  val checksum: Option[sbt.librarymanagement.Checksum],
  val allowInsecureProtocol: Boolean) extends sbt.librarymanagement.ArtifactExtra with Serializable {
  private[sbt] def validateProtocol(logger: sbt.util.Logger): Boolean = Resolver.validateArtifact(this, logger)
  def url: Option[java.net.URL] = uri.map(_.toURL)
  def withUrl(url: Option[java.net.URL]): Artifact = copy(uri = url.map(_.toURI))
  private def this(name: String) = this(name, Artifact.DefaultType, Artifact.DefaultExtension, None, Vector.empty, None, Map.empty, None, false)
  private def this(name: String, `type`: String, extension: String, classifier: Option[String], configurations: Vector[sbt.librarymanagement.ConfigRef], uri: Option[java.net.URI], extraAttributes: Map[String, String], checksum: Option[sbt.librarymanagement.Checksum]) = this(name, `type`, extension, classifier, configurations, uri, extraAttributes, checksum, false)
  
  override def equals(o: Any): Boolean = this.eq(o.asInstanceOf[AnyRef]) || (o match {
    case x: Artifact => (this.name == x.name) && (this.`type` == x.`type`) && (this.extension == x.extension) && (this.classifier == x.classifier) && (this.configurations == x.configurations) && (this.uri == x.uri) && (this.extraAttributes == x.extraAttributes) && (this.checksum == x.checksum) && (this.allowInsecureProtocol == x.allowInsecureProtocol)
    case _ => false
  })
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "sbt.librarymanagement.Artifact".##) + name.##) + `type`.##) + extension.##) + classifier.##) + configurations.##) + uri.##) + extraAttributes.##) + checksum.##) + allowInsecureProtocol.##)
  }
  override def toString: String = {
    "Artifact(" + name + ", " + `type` + ", " + extension + ", " + classifier + ", " + configurations + ", " + uri + ", " + extraAttributes + ", " + checksum + ", " + allowInsecureProtocol + ")"
  }
  private[this] def copy(name: String = name, `type`: String = `type`, extension: String = extension, classifier: Option[String] = classifier, configurations: Vector[sbt.librarymanagement.ConfigRef] = configurations, uri: Option[java.net.URI] = uri, extraAttributes: Map[String, String] = extraAttributes, checksum: Option[sbt.librarymanagement.Checksum] = checksum, allowInsecureProtocol: Boolean = allowInsecureProtocol): Artifact = {
    new Artifact(name, `type`, extension, classifier, configurations, uri, extraAttributes, checksum, allowInsecureProtocol)
  }
  def withName(name: String): Artifact = {
    copy(name = name)
  }
  def withType(`type`: String): Artifact = {
    copy(`type` = `type`)
  }
  def withExtension(extension: String): Artifact = {
    copy(extension = extension)
  }
  def withClassifier(classifier: Option[String]): Artifact = {
    copy(classifier = classifier)
  }
  def withConfigurations(configurations: Vector[sbt.librarymanagement.ConfigRef]): Artifact = {
    copy(configurations = configurations)
  }
  def withUri(uri: Option[java.net.URI]): Artifact = {
    copy(uri = uri)
  }
  def withExtraAttributes(extraAttributes: Map[String, String]): Artifact = {
    copy(extraAttributes = extraAttributes)
  }
  def withChecksum(checksum: Option[sbt.librarymanagement.Checksum]): Artifact = {
    copy(checksum = checksum)
  }
  def withAllowInsecureProtocol(allowInsecureProtocol: Boolean): Artifact = {
    copy(allowInsecureProtocol = allowInsecureProtocol)
  }
}
object Artifact extends sbt.librarymanagement.ArtifactFunctions {
  
  def apply(name: String): Artifact = new Artifact(name)
  def apply(name: String, `type`: String, extension: String, classifier: Option[String], configurations: Vector[sbt.librarymanagement.ConfigRef], uri: Option[java.net.URI], extraAttributes: Map[String, String], checksum: Option[sbt.librarymanagement.Checksum]): Artifact = new Artifact(name, `type`, extension, classifier, configurations, uri, extraAttributes, checksum)
  def apply(name: String, `type`: String, extension: String, classifier: Option[String], configurations: Vector[sbt.librarymanagement.ConfigRef], uri: Option[java.net.URI], extraAttributes: Map[String, String], checksum: Option[sbt.librarymanagement.Checksum], allowInsecureProtocol: Boolean): Artifact = new Artifact(name, `type`, extension, classifier, configurations, uri, extraAttributes, checksum, allowInsecureProtocol)
}
