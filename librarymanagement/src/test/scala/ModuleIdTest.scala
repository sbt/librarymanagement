package sbt.librarymanagement

import sbt.internal.util.UnitSpec

class ModuleIdTest extends UnitSpec {
  "Module Id" should "return cross-disabled module id as equal to a copy" in {
    ModuleID("com.acme", "foo", "1") shouldBe ModuleID("com.acme", "foo", "1")
  }
  it should "return cross-full module id as equal to a copy" in {
    (ModuleID("com.acme", "foo", "1") cross CrossVersionUtil.full) shouldBe
      (ModuleID("com.acme", "foo", "1") cross CrossVersionUtil.full)
  }
  it should "return cross-binary module id as equal to a copy" in {
    (ModuleID("com.acme", "foo", "1") cross CrossVersionUtil.binary) shouldBe
      (ModuleID("com.acme", "foo", "1") cross CrossVersionUtil.binary)
  }
}
