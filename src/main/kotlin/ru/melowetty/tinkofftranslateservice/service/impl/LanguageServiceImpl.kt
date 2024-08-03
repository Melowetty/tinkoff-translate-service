package ru.melowetty.tinkofftranslateservice.service.impl

import org.springframework.stereotype.Service
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.repository.LanguageRepository
import ru.melowetty.tinkofftranslateservice.service.LanguageService

@Service
class LanguageServiceImpl(
    private val languageRepository: LanguageRepository
): LanguageService {
    override fun getLanguages(): List<Language> {
        return languageRepository.getAllLanguages()
    }

    override fun getLanguageByCode(code: String): Language? {
        return languageRepository.getLanguageByCode(code)
    }

    override fun deleteLanguageByCode(code: String) {
        return languageRepository.deleteLanguageByCode(code)
    }

    override fun createLanguage(code: String, name: String?): Language {
        return languageRepository.createLanguage(code, name)
    }
}