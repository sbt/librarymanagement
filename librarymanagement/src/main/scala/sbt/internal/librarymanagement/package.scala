package sbt.internal

package object librarymanagement {
  implicit def sbtRichInlineConfigurationCompanion(x: InlineConfiguration.type): InlineConfigurationCompanion.type = InlineConfigurationCompanion
}
