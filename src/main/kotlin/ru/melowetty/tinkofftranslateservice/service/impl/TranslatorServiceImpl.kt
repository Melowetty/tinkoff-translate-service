package ru.melowetty.tinkofftranslateservice.service.impl

import org.springframework.stereotype.Service
import ru.melowetty.tinkofftranslateservice.model.Language
import ru.melowetty.tinkofftranslateservice.service.TranslatorService

@Service
class TranslatorServiceImpl: TranslatorService {
    override suspend fun translateWord(word: String, source: Language, target: Language): String {
        return "Translated $word"
    }
}