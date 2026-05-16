package dev.nautilus.devapi.manager.features.feature

import dev.nautilus.devapi.core.NauFeatureAPI
import dev.nautilus.devapi.manager.features.api.FeatureAPI
import dev.nautilus.devapi.manager.features.data.FeaturePaths

class PathsFeature : FeatureAPI {

    private var paths = FeaturePaths()

    override fun setFeature() {
        NauFeatureAPI.registerFeature(this)
    }

    fun configure(block: FeaturePaths.() -> FeaturePaths) {
        paths = paths.block()
    }

    fun getPaths(): FeaturePaths = paths

}