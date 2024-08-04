package ru.melowetty.tinkofftranslateservice.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.repository.TranslateRecordRepository
import ru.melowetty.tinkofftranslateservice.service.impl.TranslateServiceImpl

@ExtendWith(MockitoExtension::class)
class TranslateServiceTest {
    @InjectMocks
    private lateinit var translateService: TranslateServiceImpl
    @Mock
    private lateinit var translatorService: TranslatorService
    @Mock
    private lateinit var translateRecordRepository: TranslateRecordRepository
    @Mock
    private lateinit var languageService: LanguageService

    @Test
    fun `base translate text`() {
        runBlocking {
            val sourceLang = Language(
                1,
                "ru",
                null,
            )
            val targetLang = Language(
                2,
                "en",
                null
            )
            Mockito.`when`(translatorService.translateWord("привет", sourceLang, targetLang)).thenReturn("hi")
            Mockito.`when`(translatorService.translateWord("мир", sourceLang, targetLang)).thenReturn("world")

            Mockito.`when`(languageService.getLanguageByCode("ru")).thenReturn(sourceLang)
            Mockito.`when`(languageService.getLanguageByCode("en")).thenReturn(targetLang)
            val expected = "hi world"
            val text = "привет мир"
            val actual = translateService.translate(text, "ru", "en", "123")

            assertEquals(expected, actual)

            Mockito.verify(translateRecordRepository, Mockito.times(1)).saveTranslateRecord(any())
        }
    }

    @Test
    fun `exception when language not found`() {
        Mockito.`when`(languageService.getLanguageByCode("ru")).thenReturn(null)
        Mockito.`when`(languageService.getLanguageByCode("en")).thenReturn(Mockito.mock(Language::class.java))

        assertThrows<IllegalArgumentException> {
            translateService.translate("ru", "ru", "en", "123")
        }

        assertThrows<IllegalArgumentException> {
            translateService.translate("ru", "en", "ru", "123")
        }
    }

    @Test
    fun `exception when text is empty`() {
        Mockito.`when`(languageService.getLanguageByCode("ru")).thenReturn(Mockito.mock(Language::class.java))
        Mockito.`when`(languageService.getLanguageByCode("en")).thenReturn(Mockito.mock(Language::class.java))

        assertThrows<IllegalArgumentException> {
            translateService.translate("", "ru", "en", "123")
        }
    }
}