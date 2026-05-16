package dev.nautilus.devapi.manager.instance.api

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

interface InstanceAPI {
    fun setInstance(instance: JavaPlugin)
    fun getInstance(): JavaPlugin
    fun getMiniMessages(): MiniMessage
}