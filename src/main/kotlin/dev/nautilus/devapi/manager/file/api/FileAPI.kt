package dev.nautilus.devapi.manager.file.api

import org.bukkit.configuration.file.YamlConfiguration

interface FileAPI {
    fun saveResource(fileName: String, resourcePath: String)
    fun saveResources(resourcePath: String)
    fun getResource(fileName: String, resourcePath: String): YamlConfiguration
}