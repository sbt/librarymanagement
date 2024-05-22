/**
 * This code is generated using [[https://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** Basic SCM information for a project module */
final class ScmInfo private (
  val browseUri: java.net.URI,
  val connection: String,
  val devConnection: Option[String]) extends Serializable {
  def this(browseUrl: java.net.URL, connection: String) = this(browseUrl.toURI, connection, None)
  def this(browseUrl: java.net.URL, connection: String, devConnection: Option[String]) = this(browseUrl.toURI, connection, devConnection)
  def browseUrl: java.net.URL = browseUri.toURL
  def withBrowseUrl(browseUrl: java.net.URL): ScmInfo = copy(browseUri = browseUrl.toURI)
  private def this(browseUri: java.net.URI, connection: String) = this(browseUri, connection, None)
  
  override def equals(o: Any): Boolean = this.eq(o.asInstanceOf[AnyRef]) || (o match {
    case x: ScmInfo => (this.browseUri == x.browseUri) && (this.connection == x.connection) && (this.devConnection == x.devConnection)
    case _ => false
  })
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (17 + "sbt.librarymanagement.ScmInfo".##) + browseUri.##) + connection.##) + devConnection.##)
  }
  override def toString: String = {
    "ScmInfo(" + browseUri + ", " + connection + ", " + devConnection + ")"
  }
  private[this] def copy(browseUri: java.net.URI = browseUri, connection: String = connection, devConnection: Option[String] = devConnection): ScmInfo = {
    new ScmInfo(browseUri, connection, devConnection)
  }
  def withBrowseUri(browseUri: java.net.URI): ScmInfo = {
    copy(browseUri = browseUri)
  }
  def withConnection(connection: String): ScmInfo = {
    copy(connection = connection)
  }
  def withDevConnection(devConnection: Option[String]): ScmInfo = {
    copy(devConnection = devConnection)
  }
  def withDevConnection(devConnection: String): ScmInfo = {
    copy(devConnection = Option(devConnection))
  }
}
object ScmInfo {
  def apply(browseUrl: java.net.URL, connection: String): ScmInfo = ScmInfo(browseUrl.toURI, connection)
  def apply(browseUrl: java.net.URL, connection: String, devConnection: String): ScmInfo = ScmInfo(browseUrl.toURI, connection, devConnection)
  def apply(browseUrl: java.net.URL, connection: String, devConnection: Option[String]): ScmInfo = ScmInfo(browseUrl.toURI, connection, devConnection)
  def apply(browseUri: java.net.URI, connection: String): ScmInfo = new ScmInfo(browseUri, connection)
  def apply(browseUri: java.net.URI, connection: String, devConnection: Option[String]): ScmInfo = new ScmInfo(browseUri, connection, devConnection)
  def apply(browseUri: java.net.URI, connection: String, devConnection: String): ScmInfo = new ScmInfo(browseUri, connection, Option(devConnection))
}
