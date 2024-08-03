package ru.melowetty.tinkofftranslateservice.service.impl

import org.springframework.stereotype.Service
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.model.SupportedLanguage
import ru.melowetty.tinkofftranslateservice.service.TranslatorService

@Service
class TranslatorServiceImpl: TranslatorService {
    override suspend fun translateWord(word: String, sourceLanguage: Language, targetLanguage: Language): String {
        return "Translated $word"
    }

    override suspend fun getSupportedLanguages(): List<SupportedLanguage> {
        return listOf()
    }
}