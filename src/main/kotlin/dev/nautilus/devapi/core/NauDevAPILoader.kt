package dev.nautilus.devapi.core

import dev.nautilus.devapi.manager.features.PathsFeature
import dev.nautilus.devapi.manager.file.FileManager
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.instance.InstanceManager
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import dev.nautilus.devapi.manager.lang.LangFileManager
import dev.nautilus.devapi.manager.lang.api.LangFileAPI

internal object NauDevAPILoader {
    fun setup() {
        setupAPI()
        setupFeatures()
    }

    private fun setupAPI() {
        NauDevAPI.registerAPI<InstanceAPI>(InstanceManager())
        NauDevAPI.registerAPI<LangFileAPI>(LangFileManager())
        NauDevAPI.registerAPI<FileAPI>(FileManager())
    }

    private fun setupFeatures() {
        NauFeatureAPI.registerFeature(PathsFeature())
    }
}