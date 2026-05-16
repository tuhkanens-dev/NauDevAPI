package dev.nautilus.devapi.manager.lang

import dev.nautilus.devapi.manager.core.InstanceManager
import dev.nautilus.devapi.manager.core.NauDevAPI
import dev.nautilus.devapi.manager.lang.api.LangFileAPI
import dev.nautilus.devapi.manager.lang.api.LangAPI
import dev.nautilus.devapi.manager.lang.data.LangData
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.file.YamlConfiguration

class LangManager : LangAPI {

    companion object {
        private var CURRENT_LANG_FILE: LangData? = null
    }

    private val miniMessage = InstanceManager.getMiniMessages()

    override fun getString(key: String): String {
        return getCurrentLanguage().getString(key) ?: ""
    }

    override fun getStringList(key: String): String {
        val lines = getCurrentLanguage().getStringList(key)

        if (lines.isEmpty()) {
            getString(key)
        }

        val line = lines.joinToString("\n")
        return line
    }

    override fun getComponent(key: String, vararg placeholders: TagResolver): Component {
        val line = getString(key)

        return miniMessage.deserialize(line, TagResolver.resolver(*placeholders))
    }

    override fun getComponentList(key: String, vararg placeholders: TagResolver): Component {
        val line = getStringList(key)

        if (line.isEmpty()) {
            getComponent(key, *placeholders)
        }

        return miniMessage.deserialize(line, TagResolver.resolver(*placeholders))
    }

    override fun getCurrentLanguage(): YamlConfiguration {
        return (CURRENT_LANG_FILE
            ?: NauDevAPI.getAPI<LangFileAPI>().getLanguages().firstOrNull()
                ?.also { CURRENT_LANG_FILE = it }
            ?: throw IllegalStateException("No languages loaded!")
        ).yaml
    }

    override fun setCurrentLanguage(name: String) {
        CURRENT_LANG_FILE = NauDevAPI.getAPI<LangFileAPI>().getLanguage(name)
            ?: throw IllegalStateException("Language '$name' not found!")
    }

}