package dev.nautilus.devapi.manager.core

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

object InstanceManager {

    private lateinit var instance: JavaPlugin
    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    fun setInstance(instance: JavaPlugin) {
        this.instance = instance
    }

    fun getInstance(): JavaPlugin {
        return instance
    }

    fun getMiniMessages(): MiniMessage {
        return miniMessage
    }

}