package dev.nautilus.devapi.manager.config

import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.manager.config.api.ConfigAPI
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import dev.nautilus.devapi.manager.lang.api.LangAPI
import org.bukkit.configuration.file.FileConfiguration

class ConfigManager : ConfigAPI {

    private val plugin get() = NauDevAPI.getAPI<InstanceAPI>().getInstance()
    private val pluginConfig get() = plugin.config

    override fun loadConfig() {
        plugin.saveDefaultConfig()
        setCurrentLanguage()
    }

    private fun setCurrentLanguage() {
        val lang = pluginConfig.getString("language")
            ?: throw IllegalStateException("'language' not found in config.yml!")

        NauDevAPI.getAPI<LangAPI>().setCurrentLanguage(lang)
    }

    override fun getPluginConfig(): FileConfiguration {
        return pluginConfig
    }

}