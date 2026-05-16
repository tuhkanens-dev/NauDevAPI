package dev.nautilus.devapi.core

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

object NauDevAPI {

    val apis = ConcurrentHashMap<KClass<*>, Any>()

    inline fun <reified T : Any> registerAPI(implementation: T) {
        apis[T::class] = implementation
    }

    inline fun <reified T : Any> getAPI(): T {
        return apis[T::class] as? T
            ?: throw IllegalStateException("API for interface ${T::class.simpleName} is not registered!")
    }
}