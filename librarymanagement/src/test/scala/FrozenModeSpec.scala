package sbt.librarymanagement

import sbt.internal.librarymanagement._
import sbt.internal.librarymanagement.impl.DependencyBuilders

class FrozenModeSpec extends BaseIvySpecification with DependencyBuilders {
  private final val targetDir = Some(currentDependency)
  private final val onlineConf = makeUpdateConfiguration(false)
  private final val frozenConf = makeUpdateConfiguration(false).withFrozen(true)
  private final val noClock = LogicalClock.unknown
  private final val warningConf = UnresolvedWarningConfiguration()
  private final val normalOptions = UpdateOptions()
  private final val cachedOptions = UpdateOptions().withCachedResolution(true)

  final val stoml = Vector("me.vican.jorge" % "stoml_2.12" % "0.4" % "compile")

  it should "fail when artifacts are missing in the cache" in {
    cleanIvyCache()
    val toResolve = module(defaultModuleId, stoml, None, normalOptions)
    def update(conf: UpdateConfiguration) =
      IvyActions.updateEither(toResolve, conf, warningConf, noClock, targetDir, log)

    val onlineResolution = update(onlineConf)
    assert(onlineResolution.isRight)
    val numberResolved = onlineResolution.right.get.allModules.size

    // This relies on the fact that stoml has 5 transitive dependencies
    val frozenResolution = update(frozenConf)
    assert(frozenResolution.isRight)
    assert(frozenResolution.get.allModules.size == numberResolved,
           s"The number of resolved modules in frozen mode should be less than $numberResolved")
  }
}
