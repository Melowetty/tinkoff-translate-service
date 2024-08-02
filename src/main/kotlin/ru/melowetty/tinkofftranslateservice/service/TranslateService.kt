package ru.melowetty.tinkofftranslateservice.service

import ru.melowetty.tinkofftranslateservice.model.Language

interface TranslateService {
    fun translate(text: String, source: Language, target: Language): String
}