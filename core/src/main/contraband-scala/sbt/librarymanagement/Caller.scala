/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.librarymanagement
final class Caller private (
  val caller: sbt.librarymanagement.ModuleID,
  val callerConfigurations: Vector[String],
  val callerExtraAttributes: Map[String, String],
  val isForceDependency: Boolean,
  val isChangingDependency: Boolean,
  val isTransitiveDependency: Boolean,
  val isDirectlyForceDependency: Boolean) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: Caller => (this.caller == x.caller) && (this.callerConfigurations == x.callerConfigurations) && (this.callerExtraAttributes == x.callerExtraAttributes) && (this.isForceDependency == x.isForceDependency) && (this.isChangingDependency == x.isChangingDependency) && (this.isTransitiveDependency == x.isTransitiveDependency) && (this.isDirectlyForceDependency == x.isDirectlyForceDependency)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "Caller".##) + caller.##) + callerConfigurations.##) + callerExtraAttributes.##) + isForceDependency.##) + isChangingDependency.##) + isTransitiveDependency.##) + isDirectlyForceDependency.##)
  }
  override def toString: String = {
    s"$caller"
  }
  protected[this] def copy(caller: sbt.librarymanagement.ModuleID = caller, callerConfigurations: Vector[String] = callerConfigurations, callerExtraAttributes: Map[String, String] = callerExtraAttributes, isForceDependency: Boolean = isForceDependency, isChangingDependency: Boolean = isChangingDependency, isTransitiveDependency: Boolean = isTransitiveDependency, isDirectlyForceDependency: Boolean = isDirectlyForceDependency): Caller = {
    new Caller(caller, callerConfigurations, callerExtraAttributes, isForceDependency, isChangingDependency, isTransitiveDependency, isDirectlyForceDependency)
  }
  def withCaller(caller: sbt.librarymanagement.ModuleID): Caller = {
    copy(caller = caller)
  }
  def withCallerConfigurations(callerConfigurations: Vector[String]): Caller = {
    copy(callerConfigurations = callerConfigurations)
  }
  def withCallerExtraAttributes(callerExtraAttributes: Map[String, String]): Caller = {
    copy(callerExtraAttributes = callerExtraAttributes)
  }
  def withIsForceDependency(isForceDependency: Boolean): Caller = {
    copy(isForceDependency = isForceDependency)
  }
  def withIsChangingDependency(isChangingDependency: Boolean): Caller = {
    copy(isChangingDependency = isChangingDependency)
  }
  def withIsTransitiveDependency(isTransitiveDependency: Boolean): Caller = {
    copy(isTransitiveDependency = isTransitiveDependency)
  }
  def withIsDirectlyForceDependency(isDirectlyForceDependency: Boolean): Caller = {
    copy(isDirectlyForceDependency = isDirectlyForceDependency)
  }
}
object Caller {
  
  def apply(caller: sbt.librarymanagement.ModuleID, callerConfigurations: Vector[String], callerExtraAttributes: Map[String, String], isForceDependency: Boolean, isChangingDependency: Boolean, isTransitiveDependency: Boolean, isDirectlyForceDependency: Boolean): Caller = new Caller(caller, callerConfigurations, callerExtraAttributes, isForceDependency, isChangingDependency, isTransitiveDependency, isDirectlyForceDependency)
}
