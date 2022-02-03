package sbt.librarymanagement

import java.net.URL

/**
 * Commonly used software licenses
 * Names are SPDX ids:
 * https://raw.githubusercontent.com/spdx/license-list-data/master/json/licenses.json
 */
object License {
  lazy val Apache2: (String, URL) =
    ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))

  lazy val MIT: (String, URL) =
    ("MIT", new URL("https://opensource.org/licenses/MIT"))

  lazy val BSD_2_Clause: (String, URL) =
    ("BSD-2-Clause", new URL("https://opensource.org/licenses/BSD-2-Clause"))

  lazy val BSD_3_Clause: (String, URL) =
    ("BSD-3-Clause", new URL("https://opensource.org/licenses/BSD-3-Clause"))

  lazy val CC0: (String, URL) =
    ("CC0-1.0", new URL("https://creativecommons.org/publicdomain/zero/1.0/legalcode"))

  def PublicDomain: (String, URL) = CC0

  lazy val GPL2_or_later: (String, URL) =
    ("GPL-2.0-or-later", new URL("https://www.gnu.org/licenses/gpl-2.0.html"))

  lazy val GPL3_or_later: (String, URL) =
    ("GPL-3.0-or-later", new URL("https://spdx.org/licenses/GPL-3.0-or-later.html"))

  lazy val LGPL2_1_or_later: (String, URL) =
    ("LGPL-2.1-or-later", new URL("https://www.gnu.org/licenses/lgpl-2.1.html"))
}
