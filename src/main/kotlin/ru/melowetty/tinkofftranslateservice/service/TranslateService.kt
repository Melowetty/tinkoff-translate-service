package ru.melowetty.tinkofftranslateservice.service

import ru.melowetty.tinkofftranslateservice.model.Language

interface TranslateService {
    fun translate(text: String, sourceLanguage: Language, targetLanguage: Language, ip: String): String
}