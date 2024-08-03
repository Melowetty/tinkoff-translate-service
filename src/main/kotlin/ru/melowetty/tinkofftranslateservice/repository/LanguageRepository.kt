package ru.melowetty.tinkofftranslateservice.repository

import ru.melowetty.tinkofftranslateservice.entity.Language

interface LanguageRepository {
    fun createLanguage(code: String, name: String?): Language
    fun getLanguageByCode(code: String): Language?
    fun deleteLanguageByCode(code: String)
    fun getAllLanguages(): List<Language>
}