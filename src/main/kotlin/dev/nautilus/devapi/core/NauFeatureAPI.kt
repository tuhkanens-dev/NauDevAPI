package dev.nautilus.devapi.core

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

object NauFeatureAPI {

    val features = ConcurrentHashMap<KClass<*>, Any>()

    inline fun <reified T : Any> registerFeature(implementation: T) {
        features[T::class] = implementation
    }

    inline fun <reified T : Any> getFeature(): T {
        return features[T::class] as? T
            ?: throw IllegalStateException("FEATURE for interface ${T::class.simpleName} is not registered!")
    }

}