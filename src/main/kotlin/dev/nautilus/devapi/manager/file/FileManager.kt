package dev.nautilus.devapi.manager.file

import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.manager.instance.InstanceManager
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarFile

class FileManager : FileAPI {

    private val plugin get() = NauDevAPI.getAPI<InstanceAPI>().getInstance()
    private val dataFolder get() = plugin.dataFolder

    private val fileCache = ConcurrentHashMap<String, YamlConfiguration>()

    private fun getJarPath(): String {
        return plugin.javaClass.protectionDomain.codeSource.location.toURI().path
    }

    override fun saveResource(fileName: String, resourcePath: String) {
        val jarPath = "$resourcePath/$fileName"
        val targetFile = dataFolder.resolve(jarPath)

        plugin.logger.info("jarPath='$jarPath' targetFile='${targetFile.absolutePath}'")

        targetFile.parentFile.mkdirs()

        if (!targetFile.exists()) {
            plugin.logger.info("Trying getResource('$jarPath')")
            plugin.getResource(jarPath)?.use { input ->
                targetFile.writeBytes(input.readBytes())
            } ?: plugin.logger.warning("Resource '$jarPath' not found in JAR!")
        }
    }

    override fun saveResources(resourcePath: String) {
        val jarPath = getJarPath()
        plugin.logger.info("JAR path: $jarPath")

        JarFile(jarPath).use { jar ->
            val entries = jar.entries().asSequence()
                .filter { !it.isDirectory && it.name.startsWith("$resourcePath/") }
                .toList()

            plugin.logger.info("Found ${entries.size} entries for '$resourcePath/'")
            entries.forEach { entry ->
                plugin.logger.info("Processing entry: ${entry.name}")
                val fileName = entry.name.removePrefix("$resourcePath/")
                saveResource(fileName, resourcePath)
            }
        }
    }

    private fun getResourceResolve(file: File, jarPath: String): YamlConfiguration {
        return fileCache.getOrPut(jarPath) {
            if (file.exists()) {
                YamlConfiguration.loadConfiguration(file)
            } else {
                val input = plugin.getResource(jarPath)
                    ?: throw IllegalStateException("Resource '$jarPath' not found!")
                input.reader().use { YamlConfiguration.loadConfiguration(it) }
            }
        }
    }

    override fun getResource(fileName: String, resourcePath: String): YamlConfiguration {
        val jarPath = "$resourcePath/$fileName"
        val file = dataFolder.resolve(jarPath)

        return getResourceResolve(file, jarPath)
    }

    override fun getResource(fileName: String): YamlConfiguration {
        val file = dataFolder.resolve(fileName)

        return getResourceResolve(file, fileName)
    }
}