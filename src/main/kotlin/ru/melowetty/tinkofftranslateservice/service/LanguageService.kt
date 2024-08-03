package ru.melowetty.tinkofftranslateservice.service

import ru.melowetty.tinkofftranslateservice.entity.Language

interface LanguageService {
    fun getLanguages(): List<Language>
    fun getLanguageByCode(code: String): Language?
    fun deleteLanguageByCode(code: String)
    fun createLanguage(code: String, name: String?): Language
}