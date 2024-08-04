package ru.melowetty.tinkofftranslateservice.cron

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.model.SupportedLanguage
import ru.melowetty.tinkofftranslateservice.service.LanguageService
import ru.melowetty.tinkofftranslateservice.service.TranslatorService

@ExtendWith(MockitoExtension::class)
class LanguagesCheckWorkerTest {
    @InjectMocks
    lateinit var languagesCheckWorker: LanguagesCheckWorker
    @Mock
    lateinit var translatorService: TranslatorService
    @Mock
    lateinit var languageService: LanguageService

    @Test
    fun `run without changes`() {
        runBlocking {
            Mockito.`when`(languageService.getLanguages()).thenReturn(emptyList())
            Mockito.`when`(translatorService.getSupportedLanguages()).thenReturn(emptyList())

            languagesCheckWorker.checkLanguages()

            Mockito.verify(languageService, Mockito.never()).deleteLanguageByCode(any())
            Mockito.verify(languageService, Mockito.never()).createLanguage(any(), any())
        }
    }

    @Test
    fun `run with changes`() {
        runBlocking {
            Mockito.`when`(languageService.getLanguages()).thenReturn(
                listOf(
                    Language(1, "ru", "russian")
                )
            )
            Mockito.`when`(translatorService.getSupportedLanguages()).thenReturn(
                listOf(
                    SupportedLanguage(
                        code = "en", "english",
                    ),
                )
            )

            languagesCheckWorker.checkLanguages()

            Mockito.verify(languageService, Mockito.times(1)).deleteLanguageByCode("ru")
            Mockito.verify(languageService, Mockito.times(1)).createLanguage("en", "english")
        }
    }
}