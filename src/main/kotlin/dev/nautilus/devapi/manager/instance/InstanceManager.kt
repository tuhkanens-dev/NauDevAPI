package dev.nautilus.devapi.manager.instance

import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

class InstanceManager : InstanceAPI {

    private lateinit var instance: JavaPlugin
    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    override fun setInstance(instance: JavaPlugin) {
        this.instance = instance
    }

    override fun getInstance(): JavaPlugin {
        return instance
    }

    override fun getMiniMessages(): MiniMessage {
        return miniMessage
    }

}