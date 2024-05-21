/**
 * This code is generated using [[https://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class Developer private (
  val id: String,
  val name: String,
  val email: String,
  val uri: java.net.URI) extends Serializable {
  def this(id: String, name: String, email: String, url: java.net.URL) = this(id, name, email, url.toURI)
  def url: java.net.URL = uri.toURL
  def withUrl(url: java.net.URL): Developer = copy(uri = url.toURI)
  
  
  override def equals(o: Any): Boolean = this.eq(o.asInstanceOf[AnyRef]) || (o match {
    case x: Developer => (this.id == x.id) && (this.name == x.name) && (this.email == x.email) && (this.uri == x.uri)
    case _ => false
  })
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (17 + "sbt.librarymanagement.Developer".##) + id.##) + name.##) + email.##) + uri.##)
  }
  override def toString: String = {
    "Developer(" + id + ", " + name + ", " + email + ", " + uri + ")"
  }
  private[this] def copy(id: String = id, name: String = name, email: String = email, uri: java.net.URI = uri): Developer = {
    new Developer(id, name, email, uri)
  }
  def withId(id: String): Developer = {
    copy(id = id)
  }
  def withName(name: String): Developer = {
    copy(name = name)
  }
  def withEmail(email: String): Developer = {
    copy(email = email)
  }
  def withUri(uri: java.net.URI): Developer = {
    copy(uri = uri)
  }
}
object Developer {
  def apply(id: String, name: String, email: String, url: java.net.URL): Developer = new Developer(id, name, email, url)
  def apply(id: String, name: String, email: String, uri: java.net.URI): Developer = new Developer(id, name, email, uri)
}
