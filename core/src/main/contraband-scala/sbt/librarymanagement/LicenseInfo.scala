/**
 * This code is generated using [[https://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
/** Basic license information for a project module */
final class LicenseInfo private (
  val name: String,
  val url: java.net.URL,
  val distribution: Option[String],
  val comments: Option[String]) extends Serializable {
  
  private def this(name: String, url: java.net.URL) = this(name, url, None, None)
  
  override def equals(o: Any): Boolean = this.eq(o.asInstanceOf[AnyRef]) || (o match {
    case x: LicenseInfo => (this.name == x.name) && (this.url == x.url) && (this.distribution == x.distribution) && (this.comments == x.comments)
    case _ => false
  })
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (17 + "sbt.librarymanagement.LicenseInfo".##) + name.##) + url.##) + distribution.##) + comments.##)
  }
  override def toString: String = {
    "LicenseInfo(" + name + ", " + url + ", " + distribution + ", " + comments + ")"
  }
  private[this] def copy(name: String = name, url: java.net.URL = url, distribution: Option[String] = distribution, comments: Option[String] = comments): LicenseInfo = {
    new LicenseInfo(name, url, distribution, comments)
  }
  def withName(name: String): LicenseInfo = {
    copy(name = name)
  }
  def withUrl(url: java.net.URL): LicenseInfo = {
    copy(url = url)
  }
  def withDistribution(distribution: Option[String]): LicenseInfo = {
    copy(distribution = distribution)
  }
  def withDistribution(distribution: String): LicenseInfo = {
    copy(distribution = Option(distribution))
  }
  def withComments(comments: Option[String]): LicenseInfo = {
    copy(comments = comments)
  }
  def withComments(comments: String): LicenseInfo = {
    copy(comments = Option(comments))
  }
}
object LicenseInfo {
  
  def apply(name: String, url: java.net.URL): LicenseInfo = new LicenseInfo(name, url)
  def apply(name: String, url: java.net.URL, distribution: Option[String], comments: Option[String]): LicenseInfo = new LicenseInfo(name, url, distribution, comments)
  def apply(name: String, url: java.net.URL, distribution: String, comments: String): LicenseInfo = new LicenseInfo(name, url, Option(distribution), Option(comments))
}
