package sbt.librarymanagement

import sbt.internal.util.UnitSpec

class UpdateOptionsSpec extends UnitSpec {

  "UpdateOptions" should "have proper toString defined" in {
    UpdateOptions().toString() should be("""|UpdateOptions(
        |  circularDependencyLevel = warn,
        |  latestSnapshots = true,
        |  cachedResolution = false
        |)""".stripMargin)

    UpdateOptions()
      .withCircularDependencyLevel(CircularDependencyLevel.Error)
      .withCachedResolution(true)
      .withLatestSnapshots(false)
      .toString() should be("""|UpdateOptions(
        |  circularDependencyLevel = error,
        |  latestSnapshots = false,
        |  cachedResolution = true
        |)""".stripMargin)
  }
}
