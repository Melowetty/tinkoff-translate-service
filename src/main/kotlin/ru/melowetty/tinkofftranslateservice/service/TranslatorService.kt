package ru.melowetty.tinkofftranslateservice.service

import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.model.SupportedLanguage

interface TranslatorService {
    suspend fun translateWord(word: String, sourceLanguage: Language, targetLanguage: Language): String
    suspend fun getSupportedLanguages(): List<SupportedLanguage>
}