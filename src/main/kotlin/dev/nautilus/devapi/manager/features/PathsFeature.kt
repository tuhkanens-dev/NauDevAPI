package dev.nautilus.devapi.manager.features

import dev.nautilus.devapi.manager.features.data.FeaturePaths

class PathsFeature {

    private var paths = FeaturePaths()

    fun configure(block: FeaturePaths.() -> FeaturePaths) {
        paths = paths.block()
    }

    fun getPaths(): FeaturePaths = paths

}