package dev.nautilus.devapi.manager.lang.data

import org.bukkit.configuration.file.YamlConfiguration

data class LangData(
    val name: String,
    val yaml: YamlConfiguration
)