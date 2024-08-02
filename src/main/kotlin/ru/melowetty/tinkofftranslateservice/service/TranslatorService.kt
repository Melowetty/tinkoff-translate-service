package ru.melowetty.tinkofftranslateservice.service

import ru.melowetty.tinkofftranslateservice.model.Language

interface TranslatorService {
    suspend fun translateWord(word: String, sourceLanguage: Language, targetLanguage: Language): String
}