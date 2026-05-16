package dev.nautilus.devapi.manager.lang

import dev.nautilus.devapi.manager.instance.InstanceManager
import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.core.NauFeatureAPI
import dev.nautilus.devapi.manager.features.feature.PathsFeature
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import dev.nautilus.devapi.manager.lang.api.LangFileAPI
import dev.nautilus.devapi.manager.lang.data.LangData
import org.bukkit.configuration.file.YamlConfiguration

class LangFileManager : LangFileAPI {

    private val plugin = NauDevAPI.getAPI<InstanceAPI>().getInstance()
    private val dataFolder = plugin.dataFolder

    private var langFiles: MutableList<LangData> = mutableListOf()
    private val langFolder = NauFeatureAPI.getFeature<PathsFeature>().getPaths().langFolder

    override fun loadLanguages() {

        NauDevAPI.getAPI<FileAPI>().saveResources(langFolder)

        dataFolder.resolve(langFolder).listFiles()?.forEach { file ->
            val yaml = YamlConfiguration.loadConfiguration(file)
            langFiles.add(LangData(name = file.nameWithoutExtension, yaml = yaml))
        }

    }

    override fun getLanguage(name: String): LangData? {
        return langFiles.firstOrNull { it.name == name }
    }

    override fun getLanguages(): List<LangData> {
        return langFiles
    }

}