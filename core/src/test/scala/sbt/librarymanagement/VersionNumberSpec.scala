package sbt.librarymanagement

import org.scalatest.{ FreeSpec, Inside, Matchers }

// This is a specification to check VersionNumber and VersionNumberCompatibility.
class VersionNumberSpec extends FreeSpec with Matchers with Inside {
  import VersionNumber.{ SemVer, SecondSegment }

  version("1") { v =>
    assertParsesTo(v, Seq(1), Seq(), Seq())
    assertBreaksDownTo(v, Some(1))
    assertCascadesTo(v, Seq("1"))
  }

  version("1.0") { v =>
    assertParsesTo(v, Seq(1, 0), Seq(), Seq())
    assertBreaksDownTo(v, Some(1), Some(0))
    assertCascadesTo(v, Seq("1.0"))
  }

  version("1.0.0") { v =>
    assertParsesTo(v, Seq(1, 0, 0), Seq(), Seq())
    assertBreaksDownTo(v, Some(1), Some(0), Some(0))
    assertCascadesTo(v, Seq("1.0.0", "1.0"))

    assertIsCompatibleWith(v, "1.0.1", SemVer)
    assertIsCompatibleWith(v, "1.1.1", SemVer)
    assertIsNotCompatibleWith(v, "2.0.0", SemVer)
    assertIsNotCompatibleWith(v, "1.0.0-M1", SemVer)

    assertIsCompatibleWith(v, "1.0.1", SecondSegment)
    assertIsNotCompatibleWith(v, "1.1.1", SecondSegment)
    assertIsNotCompatibleWith(v, "2.0.0", SecondSegment)
    assertIsNotCompatibleWith(v, "1.0.0-M1", SecondSegment)
  }

  version("1.0.0.0") { v =>
    assertParsesTo(v, Seq(1, 0, 0, 0), Seq(), Seq())
    assertBreaksDownTo(v, Some(1), Some(0), Some(0), Some(0))
    assertCascadesTo(v, Seq("1.0.0.0", "1.0.0", "1.0"))
  }

  version("0.12.0") { v =>
    assertParsesTo(v, Seq(0, 12, 0), Seq(), Seq())
    assertBreaksDownTo(v, Some(0), Some(12), Some(0))
    assertCascadesTo(v, Seq("0.12.0", "0.12"))

    assertIsNotCompatibleWith(v, "0.12.0-RC1", SemVer)
    assertIsNotCompatibleWith(v, "0.12.1", SemVer)
    assertIsNotCompatibleWith(v, "0.12.1-M1", SemVer)

    assertIsNotCompatibleWith(v, "0.12.0-RC1", SecondSegment)
    assertIsCompatibleWith(v, "0.12.1", SecondSegment)
    assertIsCompatibleWith(v, "0.12.1-M1", SecondSegment)
  }

  version("0.1.0-SNAPSHOT") { v =>
    assertParsesTo(v, Seq(0, 1, 0), Seq("SNAPSHOT"), Seq())
    assertCascadesTo(v, Seq("0.1.0-SNAPSHOT", "0.1.0", "0.1"))

    assertIsCompatibleWith(v, "0.1.0-SNAPSHOT", SemVer)
    assertIsNotCompatibleWith(v, "0.1.0", SemVer)
    assertIsCompatibleWith(v, "0.1.0-SNAPSHOT+001", SemVer)

    assertIsCompatibleWith(v, "0.1.0-SNAPSHOT", SecondSegment)
    assertIsNotCompatibleWith(v, "0.1.0", SecondSegment)
    assertIsCompatibleWith(v, "0.1.0-SNAPSHOT+001", SecondSegment)
  }

  version("0.1.0-M1") { v =>
    assertParsesTo(v, Seq(0, 1, 0), Seq("M1"), Seq())
    assertCascadesTo(v, Seq("0.1.0-M1", "0.1.0", "0.1"))
  }

  version("0.1.0-RC1") { v =>
    assertParsesTo(v, Seq(0, 1, 0), Seq("RC1"), Seq())
    assertCascadesTo(v, Seq("0.1.0-RC1", "0.1.0", "0.1"))
  }

  version("0.1.0-MSERVER-1") { v =>
    assertParsesTo(v, Seq(0, 1, 0), Seq("MSERVER", "1"), Seq())
    assertCascadesTo(v, Seq("0.1.0-MSERVER-1", "0.1.0", "0.1"))
  }

  version("2.10.4-20140115-000117-b3a-sources") { v =>
    assertParsesTo(v, Seq(2, 10, 4), Seq("20140115", "000117", "b3a", "sources"), Seq())
    assertCascadesTo(v, Seq("2.10.4-20140115-000117-b3a-sources", "2.10.4", "2.10"))
    assertIsCompatibleWith(v, "2.0.0", SemVer)
    assertIsNotCompatibleWith(v, "2.0.0", SecondSegment)
  }

  version("20140115000117-b3a-sources") { v =>
    assertParsesTo(v, Seq(20140115000117L), Seq("b3a", "sources"), Seq())
    assertCascadesTo(v, Seq("20140115000117-b3a-sources"))
  }

  version("1.0.0-alpha+001+002") { v =>
    assertParsesTo(v, Seq(1, 0, 0), Seq("alpha"), Seq("+001", "+002"))
    assertCascadesTo(v, Seq("1.0.0-alpha+001+002", "1.0.0", "1.0"))
  }

  version("non.space.!?string") { v =>
    assertParsesTo(v, Seq(), Seq(), Seq("non.space.!?string"))
    assertCascadesTo(v, Seq("non.space.!?string"))
  }

  version("space !?string") { v =>
    assertParsesToError(v)
  }
  version("") { v =>
    assertParsesToError(v)
  }

  "VersionNumber" - {
    "should have an ordering" in {
      val versions = List(
        "2.0.0",
        "1.1.0",
        "1.0.2",
        "1.0.1",
        "1.0.0"
      )
      val expected = List(
        "1.0.0",
        "1.0.1",
        "1.0.2",
        "1.1.0",
        "2.0.0"
      )
      assertSortTo(versions, expected)
    }

    "should put a lower precedence to a pre-release version than a normal version" in {
      val versions = List(
        "1.0.0",
        "1.0.1",
        "1.0.0-SNAPSHOT",
        "1.0.1-SNAPSHOT"
      )
      val expected = List(
        "1.0.0-SNAPSHOT",
        "1.0.0",
        "1.0.1-SNAPSHOT",
        "1.0.1"
      )
      assertSortTo(versions, expected)
    }

    "should put a higher precedence to a version with larger set of pre-release version's fields " +
      "than a that with smaller set of the fields, if all of the preceding identifiers are equal" in {
      val versions = List(
        "1.0.0-alpha-beta-gamma",
        "1.0.0-alpha-beta",
        "1.0.0-alpha-bar",
        "1.0.0-alpha"
      )
      val expected = List(
        "1.0.0-alpha",
        "1.0.0-alpha-bar",
        "1.0.0-alpha-beta",
        "1.0.0-alpha-beta-gamma"
      )
      assertSortTo(versions, expected)
    }

    "should put a lower precedence to a pre-release version with numerical identifiers " +
      "than a that with non-numerical identifiers" in {
      val versions = List(
        "1.0.0-alpha-3",
        "1.0.0-alpha-beta",
        "1.0.0-alpha-1a",
        "1.0.0-alpha"
      )
      val expected = List(
        "1.0.0-alpha",
        "1.0.0-alpha-3",
        "1.0.0-alpha-1a",
        "1.0.0-alpha-beta"
      )
      assertSortTo(versions, expected)
    }

    "should compare pre-release versions numerically if the pre-release versions have only digits" in {
      val versions = List(
        "1.0.0-alpha",
        "1.0.0-alpha-11",
        "1.0.0-alpha-3",
        "1.0.0-alpha-22"
      )
      val expected = List(
        "1.0.0-alpha",
        "1.0.0-alpha-3",
        "1.0.0-alpha-11",
        "1.0.0-alpha-22"
      )
      assertSortTo(versions, expected)
    }

    "should compare pre-release versions that have non-numerical letters in lexical order ignoring their case" in {
      val versions = List(
        "1.0.0-SNAPSHOT",
        "1.0.0",
        "1.0.0-M1",
        "1.0.0-M2",
        "1.0.0-alpha",
        "1.0.0-BETA"
      )
      val expected = List(
        "1.0.0-alpha",
        "1.0.0-BETA",
        "1.0.0-M1",
        "1.0.0-M2",
        "1.0.0-SNAPSHOT",
        "1.0.0"
      )
      assertSortTo(versions, expected)
    }
  }

  ////

  private[this] final class VersionString(val value: String)

  private[this] def version(s: String)(f: VersionString => Unit) =
    s"""Version "$s"""" - {
      f(new VersionString(s))
    }

  private[this] def assertParsesTo(
      v: VersionString,
      ns: Seq[Long],
      ts: Seq[String],
      es: Seq[String]
  ): Unit =
    s"should parse to ($ns, $ts, $es)" in inside(v.value) {
      case VersionNumber(ns1, ts1, es1) =>
        (ns1 shouldBe ns)
        (ts1 shouldBe ts)
        (es1 shouldBe es)
        (VersionNumber(ns, ts, es).toString shouldBe v.value)
        (VersionNumber(ns, ts, es) shouldBe VersionNumber(ns, ts, es))
    }

  private[this] def assertParsesToError(v: VersionString): Unit =
    "should parse as an error" in {
      v.value should not matchPattern {
        case s: String if VersionNumber.unapply(s).isDefined => // because of unapply overloading
      }
    }

  private[this] def assertBreaksDownTo(
      v: VersionString,
      major: Option[Long],
      minor: Option[Long] = None,
      patch: Option[Long] = None,
      buildNumber: Option[Long] = None
  ): Unit =
    s"should breakdown to ($major, $minor, $patch, $buildNumber)" in inside(v.value) {
      case VersionNumber(ns, ts, es) =>
        val v = VersionNumber(ns, ts, es)
        (v._1 shouldBe major)
        (v._2 shouldBe minor)
        (v._3 shouldBe patch)
        (v._4 shouldBe buildNumber)
    }

  private[this] def assertCascadesTo(v: VersionString, ns: Seq[String]): Unit = {
    s"should cascade to $ns" in {
      val versionNumbers = ns.toVector map VersionNumber.apply
      VersionNumber(v.value).cascadingVersions shouldBe versionNumbers
    }
  }

  private[this] def assertIsCompatibleWith(
      v1: VersionString,
      v2: String,
      vnc: VersionNumberCompatibility
  ): Unit =
    checkCompat(true, vnc, v1, v2)

  private[this] def assertIsNotCompatibleWith(
      v1: VersionString,
      v2: String,
      vnc: VersionNumberCompatibility
  ): Unit =
    checkCompat(false, vnc, v1, v2)

  private[this] def checkCompat(
      expectOutcome: Boolean,
      vnc: VersionNumberCompatibility,
      v1: VersionString,
      v2: String
  ) = {
    val prefix = if (expectOutcome) "should" else "should NOT"
    val compatibilityStrategy = vnc match {
      case SemVer        => "SemVer"
      case SecondSegment => "SecondSegment"
      case _             => val s = vnc.name; if (s contains " ") s""""$s"""" else s
    }
    s"$prefix be $compatibilityStrategy compatible with $v2" in {
      vnc.isCompatible(VersionNumber(v1.value), VersionNumber(v2)) shouldBe expectOutcome
    }
  }
  def assertSortTo(versions: List[String], expected: List[String]) = {
    versions.map(VersionNumber.apply).sorted shouldBe expected.map(VersionNumber.apply)
  }
}
