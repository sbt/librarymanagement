package sbt.internal.librarymanagement

import sbt.librarymanagement.ivy._
import verify.BasicTestSuite

class UpdateOptionsSpec extends BasicTestSuite {
  test("UpdateOptions should have proper toString defined") {
    assert(UpdateOptions().toString() == """|UpdateOptions(
        |  circularDependencyLevel = warn,
        |  latestSnapshots = true,
        |  cachedResolution = false
        |)""".stripMargin)

    assert(
      UpdateOptions()
        .withCircularDependencyLevel(CircularDependencyLevel.Error)
        .withCachedResolution(true)
        .withCachedSnapshots(true)
        .toString() == """|UpdateOptions(
        |  circularDependencyLevel = error,
        |  cachedSnapshots = true,
        |  cachedResolution = true
        |)""".stripMargin
    )
  }
}
