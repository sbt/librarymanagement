package sbt.librarymanagement

import scala.annotation.tailrec

final class VersionNumber private[sbt] (
    val numbers: Seq[Long],
    val tags: Seq[String],
    val extras: Seq[String]
) {

  def _1: Option[Long] = get(0)
  def _2: Option[Long] = get(1)
  def _3: Option[Long] = get(2)
  def _4: Option[Long] = get(3)
  def get(idx: Int): Option[Long] = numbers lift idx
  def size: Int = numbers.size

  def <(o: VersionNumber): Boolean = implicitly[Ordering[VersionNumber]].lt(this, o)
  def <=(o: VersionNumber): Boolean = implicitly[Ordering[VersionNumber]].lteq(this, o)
  def >(o: VersionNumber): Boolean = implicitly[Ordering[VersionNumber]].gt(this, o)
  def >=(o: VersionNumber): Boolean = implicitly[Ordering[VersionNumber]].gteq(this, o)

  /** The vector of version numbers from more to less specific from this version number. */
  lazy val cascadingVersions: Vector[VersionNumber] =
    (Vector(this) ++ (numbers.inits filter (_.size >= 2) map (VersionNumber(_, Nil, Nil)))).distinct

  override val toString: String =
    numbers.mkString(".") + mkString1(tags, "-", "-", "") + extras.mkString("")

  override def hashCode: Int = numbers.## * 41 * 41 + tags.## * 41 + extras.##

  override def equals(that: Any): Boolean = that match {
    case v: VersionNumber => (numbers == v.numbers) && (tags == v.tags) && (extras == v.extras)
    case _                => false
  }

  /** A variant of mkString that returns the empty string if the sequence is empty. */
  private[this] def mkString1[A](xs: Seq[A], start: String, sep: String, end: String): String =
    if (xs.isEmpty) "" else xs.mkString(start, sep, end)
}

object VersionNumber {

  /**
   * @param numbers numbers delimited by a dot.
   * @param tags string prefixed by a dash.
   * @param extras strings at the end.
   */
  def apply(numbers: Seq[Long], tags: Seq[String], extras: Seq[String]): VersionNumber =
    new VersionNumber(numbers, tags, extras)

  def apply(s: String): VersionNumber =
    unapply(s) match {
      case Some((ns, ts, es)) => VersionNumber(ns, ts, es)
      case _                  => throw new IllegalArgumentException(s"Invalid version number: $s")
    }

  def unapply(v: VersionNumber): Option[(Seq[Long], Seq[String], Seq[String])] =
    Some((v.numbers, v.tags, v.extras))

  def unapply(s: String): Option[(Seq[Long], Seq[String], Seq[String])] = {

    // null safe, empty string safe
    def splitOn[A](s: String, sep: Char): Vector[String] =
      if (s eq null) Vector()
      else s.split(sep).filterNot(_ == "").toVector

    def splitDot(s: String) = splitOn(s, '.') map (_.toLong)
    def splitDash(s: String) = splitOn(s, '-')
    def splitPlus(s: String) = splitOn(s, '+') map ("+" + _)

    val TaggedVersion = """(\d{1,14})([\.\d{1,14}]*)((?:-\w+)*)((?:\+.+)*)""".r
    val NonSpaceString = """(\S+)""".r

    s match {
      case TaggedVersion(m, ns, ts, es) =>
        val numbers = Vector(m.toLong) ++ splitDot(ns)
        val tags = splitDash(ts)
        val extras = splitPlus(es)
        Some((numbers, tags, extras))
      case ""                => None
      case NonSpaceString(s) => Some((Vector.empty, Vector.empty, Vector(s)))
      case _                 => None
    }
  }

  implicit val versionNumberOrdering: Ordering[VersionNumber] =
    new Ordering[VersionNumber] {
      def compare(x: VersionNumber, y: VersionNumber): Int = {
        compareSeq(x.numbers, y.numbers) match {
          case 0 =>
            comparePreRelease(x, y) match {
              case 0      => compareSeq(x.extras, y.extras)
              case resPre => resPre
            }
          case resNormal => resNormal
        }
      }
    }

  @tailrec
  private def compareSeq[A: Ordering: Zero](a1: Seq[A], a2: Seq[A]): Int =
    if (a1.isEmpty && a2.isEmpty) 0
    else if (a1.nonEmpty && a2.isEmpty)
      implicitly[Ordering[A]].compare(a1.head, implicitly[Zero[A]].zero)
    else if (a1.isEmpty && a2.nonEmpty)
      implicitly[Ordering[A]].compare(implicitly[Zero[A]].zero, a2.head)
    else {
      val a1head = a1.head
      val a2head = a2.head
      if (a1head == a2head) compareSeq(a1.tail, a2.tail)
      else implicitly[Ordering[A]].compare(a1head, a2head)
    }

  private def comparePreRelease(v1: VersionNumber, v2: VersionNumber): Int = {
    implicit val PreReleaseStringOrdering: Ordering[String] = new Ordering[String] {
      def compare(x: String, y: String): Int = {
        (x.isEmpty, y.isEmpty) match {
          case (true, true)  => 0
          case (false, true) => 1
          case (true, false) => -1
          case (false, false) =>
            (x.matches("""\d+"""), y.matches("""\d+""")) match {
              // Identifiers consisting of only digits are compared numerically.
              // Numeric identifiers always have lower precedence than non-numeric identifiers.
              // Identifiers with letters are compared case insensitive lexical order.
              case (true, true)   => implicitly[Ordering[Int]].compare(x.toInt, y.toInt)
              case (false, true)  => 1
              case (true, false)  => -1
              case (false, false) => x.toLowerCase.compareTo(y.toLowerCase)
            }
        }
      }
    }
    val p1 = v1.tags
    val p2 = v2.tags
    if (p1.isEmpty && p2.isEmpty) 0
    else if (p1.nonEmpty && p2.isEmpty) -1
    else if (p1.isEmpty && p2.nonEmpty) 1
    else compareSeq(p1, p2)
  }

  /** Strict. Checks everything. */
  object Strict extends VersionNumberCompatibility {
    def name: String = "Strict"
    def isCompatible(v1: VersionNumber, v2: VersionNumber): Boolean = v1 == v2
  }

  /** Semantic Versioning. See http://semver.org/spec/v2.0.0.html */
  object SemVer extends VersionNumberCompatibility {
    def name: String = "Semantic Versioning"

    /* Quotes of parts of the rules in the SemVer Spec relevant to compatibility checking:
     *
     * Rule 2:
     * > A normal version number MUST take the form X.Y.Z
     *
     * Rule 4:
     * > Major version zero (0.y.z) is for initial development. Anything may change at any time.
     *
     * Rule 6:
     * > Patch version Z (x.y.Z | x > 0) MUST be incremented if only backwards compatible bug fixes are introduced.
     *
     * Rule 7:
     * > Minor version Y (x.Y.z | x > 0) MUST be incremented if new, backwards compatible functionality is introduced.
     *
     * Rule 8:
     * > Major version X (X.y.z | X > 0) MUST be incremented if any backwards incompatible changes are introduced.
     *
     * Rule 9:
     * > A pre-release version MAY be denoted by appending a hyphen and a series of
     * > dot separated identifiers immediately following the patch version.
     * > Identifiers MUST comprise only ASCII alphanumerics and hyphen [0-9A-Za-z-].
     * > Identifiers MUST NOT be empty.
     * > Numeric identifiers MUST NOT include leading zeroes.
     * > Pre-release versions have a lower precedence than the associated normal version.
     * > A pre-release version indicates that the version is unstable and might not satisfy the
     * > intended compatibility requirements as denoted by its associated normal version.
     * > Examples: 1.0.0-alpha, 1.0.0-alpha.1, 1.0.0-0.3.7, 1.0.0-x.7.z.92.
     *
     * Rule 10:
     * > Build metadata MAY be denoted by appending a plus sign and a series of
     * > dot separated identifiers immediately following the patch or pre-release version.
     * > Identifiers MUST comprise only ASCII alphanumerics and hyphen [0-9A-Za-z-].
     * > Identifiers MUST NOT be empty.
     * > Build metadata SHOULD be ignored when determining version precedence.
     * > Thus two versions that differ only in the build metadata, have the same precedence.
     * > Examples: 1.0.0-alpha+001, 1.0.0+20130313144700, 1.0.0-beta+exp.sha.5114f85.
     *
     * Rule 10 means that build metadata is never considered for compatibility
     *         we'll enforce this immediately by dropping them from both versions
     * Rule 2 we enforce with custom extractors.
     * Rule 4 we enforce by matching x = 0 & fully equals checking the two versions
     * Rule 6, 7 & 8 means version compatibility is determined by comparing the two X values
     * Rule 9..
     *   Dale thinks means pre-release versions are fully equals checked..
     *   Eugene thinks means pre-releases before 1.0.0 are not compatible, if not they are..
     */
    def isCompatible(v1: VersionNumber, v2: VersionNumber): Boolean =
      doIsCompat(dropBuildMetadata(v1), dropBuildMetadata(v2))

    private[this] def doIsCompat(v1: VersionNumber, v2: VersionNumber): Boolean =
      (v1, v2) match {
        case (NormalVersion(0, _, _), NormalVersion(0, _, _))   => v1 == v2 // R4
        case (NormalVersion(_, 0, 0), NormalVersion(_, 0, 0))   => v1 == v2 // R9 maybe?
        case (NormalVersion(x1, _, _), NormalVersion(x2, _, _)) => x1 == x2 // R6, R7 & R8
        case _                                                  => false
      }

    // SemVer Spec Rule 10 (above)
    private[VersionNumber] def dropBuildMetadata(v: VersionNumber) =
      if (v.extras.isEmpty) v else VersionNumber(v.numbers, v.tags, Nil)

    // An extractor for SemVer's "normal version number" - SemVer Spec Rule 2 & Rule 9 (above)
    private[VersionNumber] object NormalVersion {
      def unapply(v: VersionNumber): Option[(Long, Long, Long)] =
        PartialFunction.condOpt(v.numbers) {
          // NOTE! We allow the z to be missing, because of legacy like commons-io 1.3
          case Seq(x, y, _*) => (x, y, v._3 getOrElse 0)
        }
    }
  }

  /** A variant of SemVar that seems to be common among the Scala libraries.
   * The second segment (y in x.y.z) increments breaks the binary compatibility even when x > 0.
   * Also API compatibility is expected even when the first segment is zero.
   */
  object SecondSegment extends VersionNumberCompatibility {
    import SemVer._

    def name: String = "Second Segment Variant"

    def isCompatible(v1: VersionNumber, v2: VersionNumber): Boolean =
      doIsCompat(dropBuildMetadata(v1), dropBuildMetadata(v2))

    private[this] def doIsCompat(v1: VersionNumber, v2: VersionNumber): Boolean = {
      (v1, v2) match {
        case (NormalVersion(_, _, 0), NormalVersion(_, _, 0))     => v1 == v2 // R9 maybe?
        case (NormalVersion(x1, y1, _), NormalVersion(x2, y2, _)) => (x1 == x2) && (y1 == y2)
        case _                                                    => false
      }
    }
  }

  private trait Zero[A] {
    def zero: A
  }
  private object Zero {
    def apply[A](z: => A): Zero[A] = new Zero[A] {
      def zero: A = z
    }
    implicit val StringZero: Zero[String] = Zero[String]("")
    implicit val LongZero: Zero[Long] = Zero[Long](0L)
  }
}

trait VersionNumberCompatibility {
  def name: String
  def isCompatible(v1: VersionNumber, v2: VersionNumber): Boolean
}
