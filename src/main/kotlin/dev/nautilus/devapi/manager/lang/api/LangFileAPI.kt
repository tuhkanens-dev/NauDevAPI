package dev.nautilus.devapi.manager.lang.api

import dev.nautilus.devapi.manager.lang.data.LangData

interface LangFileAPI {
    fun loadLanguages()
    fun getLanguage(name: String): LangData?
    fun getLanguages(): List<LangData>
}