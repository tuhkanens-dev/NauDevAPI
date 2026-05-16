package dev.nautilus.devapi.manager.config

import dev.nautilus.devapi.manager.core.InstanceManager
import dev.nautilus.devapi.manager.core.NauDevAPI
import dev.nautilus.devapi.manager.lang.api.LangAPI
import org.bukkit.configuration.file.FileConfiguration

object ConfigManager {

    private val plugin = InstanceManager.getInstance()
    private val config = plugin.config

    fun loadConfig() {
        plugin.saveDefaultConfig()
        setCurrentLanguage()
    }

    private fun setCurrentLanguage() {
        val lang = config.getString("language")
            ?: throw IllegalStateException("'language' not found in config.yml!")

        NauDevAPI.getAPI<LangAPI>().setCurrentLanguage(lang)
    }

    fun getConfig(): FileConfiguration {
        return config
    }

}