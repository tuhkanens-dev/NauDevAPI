package dev.nautilus.devapi.manager.config.api

import org.bukkit.configuration.file.FileConfiguration

interface ConfigAPI {
    fun loadConfig()
    fun getPluginConfig(): FileConfiguration
}