package dev.nautilus.devapi.manager.config

import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.manager.config.api.ConfigAPI
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import dev.nautilus.devapi.manager.lang.api.LangAPI
import org.bukkit.configuration.file.FileConfiguration

class ConfigManager : ConfigAPI {

    private val plugin = NauDevAPI.getAPI<InstanceAPI>().getInstance()
    private val config = plugin.config

    override fun loadConfig() {
        plugin.saveDefaultConfig()
        setCurrentLanguage()
    }

    private fun setCurrentLanguage() {
        val lang = config.getString("language")
            ?: throw IllegalStateException("'language' not found in config.yml!")

        NauDevAPI.getAPI<LangAPI>().setCurrentLanguage(lang)
    }

    override fun getConfig(): FileConfiguration {
        return config
    }

}