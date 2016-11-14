/* sbt -- Simple Build Tool
 * Copyright 2008, 2009, 2010  Mark Harrah
 */
package sbt.internal.librarymanagement

import sbt.librarymanagement._

abstract class InlineConfigurationCompanion {
  def configurations(explicitConfigurations: Iterable[Configuration], defaultConfiguration: Option[Configuration]) =
    if (explicitConfigurations.isEmpty) {
      defaultConfiguration match {
        case Some(Configurations.DefaultIvyConfiguration) => Configurations.Default :: Nil
        case Some(Configurations.DefaultMavenConfiguration) => Configurations.defaultMavenConfigurations
        case _ => Nil
      }
    } else
      explicitConfigurations
}
