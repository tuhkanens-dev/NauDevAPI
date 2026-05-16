package dev.nautilus.devapi.manager.lang.api

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.file.YamlConfiguration

interface LangAPI {
    fun getString(key: String): String
    fun getStringList(key: String): String
    fun getComponent(key: String, vararg placeholders: TagResolver): Component
    fun getComponentList(key: String, vararg placeholders: TagResolver): Component
    fun getCurrentLanguage(): YamlConfiguration
    fun setCurrentLanguage(name: String)
}