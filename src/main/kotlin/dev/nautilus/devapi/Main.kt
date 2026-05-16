package dev.nautilus.devapi

import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.core.NauFeatureAPI
import dev.nautilus.devapi.manager.features.PathsFeature
import dev.nautilus.devapi.manager.file.FileManager
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.instance.InstanceManager
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import dev.nautilus.devapi.manager.lang.LangFileManager
import dev.nautilus.devapi.manager.lang.api.LangFileAPI
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        // Plugin enable logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
