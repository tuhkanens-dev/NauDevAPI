package dev.nautilus.devapi.manager.file

import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.manager.instance.InstanceManager
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarFile

class FileManager : FileAPI {

    private val plugin = NauDevAPI.getAPI<InstanceAPI>().getInstance()
    private val dataFolder = plugin.dataFolder

    private val fileCache = ConcurrentHashMap<String, YamlConfiguration>()

    private fun getJarPath(): String {
        return plugin.javaClass.protectionDomain.codeSource.location.toURI().path
    }

    override fun saveResource(fileName: String, resourcePath: String) {
        val resourcePath = Path.of(resourcePath, fileName).toString()
        val targetFile = dataFolder.resolve(resourcePath)

        targetFile.parentFile.mkdir()

        if (!targetFile.exists()) {
            plugin.getResource(resourcePath)?.use { input ->
                targetFile.writeBytes(input.readBytes())
            }
        }
    }

    override fun saveResources(resourcePath: String) {
        JarFile(getJarPath()).use { jar ->
            jar.entries().asSequence()
                .filter { !it.isDirectory && it.name.startsWith("$resourcePath/") }
                .forEach { entry ->
                    val fileName = entry.name.removePrefix("$resourcePath/")
                    saveResource(fileName, resourcePath)
                }
        }
    }

    override fun getResource(fileName: String, resourcePath: String): YamlConfiguration {
        val path = Path.of(resourcePath, fileName).toString()

        return fileCache.getOrPut(path) {
            val file = dataFolder.resolve(path)

            if (file.exists()) {
                YamlConfiguration.loadConfiguration(file)
            } else {
                val input = plugin.getResource(fileName)
                    ?: throw IllegalStateException("Resource '$path' not found!")
                input.reader().use { YamlConfiguration.loadConfiguration(it) }
            }
        }
    }
}