package dev.nautilus.devapi.manager.file

import dev.nautilus.devapi.core.NauDevAPI
import dev.nautilus.devapi.manager.file.api.FileAPI
import dev.nautilus.devapi.manager.instance.api.InstanceAPI
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarFile

class FileManager : FileAPI {

    private val plugin get() = NauDevAPI.getAPI<InstanceAPI>().getInstance()
    private val dataFolder get() = plugin.dataFolder

    private val fileCache = ConcurrentHashMap<String, YamlConfiguration>()

    private fun getJarPath(): String {
        return plugin.javaClass.protectionDomain.codeSource.location.toURI().path
    }

    private fun buildJarPath(fileName: String, resourcePath: String): String {
        return if (resourcePath.isEmpty()) fileName else "$resourcePath/$fileName"
    }

    override fun saveResource(fileName: String, resourcePath: String) {
        val jarPath = buildJarPath(fileName, resourcePath)
        val targetFile = dataFolder.resolve(jarPath)

        targetFile.parentFile.mkdirs()

        if (!targetFile.exists()) {
            plugin.getResource(jarPath)?.use { input ->
                targetFile.writeBytes(input.readBytes())
            } ?: plugin.logger.warning("Resource '$jarPath' not found in JAR!")
        }
    }

    override fun saveResource(fileName: String) {
        saveResource(fileName, "")
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

    override fun getResource(fileName: String, resourcePath: String): YamlConfiguration {
        val jarPath = buildJarPath(fileName, resourcePath)
        val file = dataFolder.resolve(jarPath)
        return getResourceResolve(file, jarPath)
    }

    override fun getResource(fileName: String): YamlConfiguration {
        return getResource(fileName, "")
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
}