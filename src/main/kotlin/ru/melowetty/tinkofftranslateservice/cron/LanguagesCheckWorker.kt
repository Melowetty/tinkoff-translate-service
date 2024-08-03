package ru.melowetty.tinkofftranslateservice.cron

import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.melowetty.tinkofftranslateservice.model.SupportedLanguage
import ru.melowetty.tinkofftranslateservice.service.LanguageService
import ru.melowetty.tinkofftranslateservice.service.TranslatorService

@Component
class LanguagesCheckWorker(
    private val translatorService: TranslatorService,
    private val languageService: LanguageService
) {
    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000, initialDelay = 5_000)
    @Async
    fun checkLanguages() {
        runBlocking {
            val current = languageService.getLanguages().map {
                SupportedLanguage(
                    code = it.code,
                    name = it.name,
                )
            }.toSet()

            val actual = translatorService.getSupportedLanguages().toSet()

            val deleted = current.minus(actual)
            val added = actual.minus(current)

            deleted.forEach {
                languageService.deleteLanguageByCode(it.code)
            }

            added.forEach {
                languageService.createLanguage(name = it.name, code = it.code)
            }
        }
    }
}