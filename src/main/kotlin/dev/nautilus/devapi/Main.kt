package dev.nautilus.devapi

import dev.nautilus.devapi.manager.core.NauDevAPI
import dev.nautilus.devapi.manager.file.FileManager
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.lang.LangFileManager
import dev.nautilus.devapi.manager.lang.api.LangFileAPI
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        val langManager = LangFileManager()
        NauDevAPI.registerAPI<LangFileAPI>(langManager)

        val fileManager = FileManager()
        NauDevAPI.registerAPI<FileAPI>(fileManager)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
