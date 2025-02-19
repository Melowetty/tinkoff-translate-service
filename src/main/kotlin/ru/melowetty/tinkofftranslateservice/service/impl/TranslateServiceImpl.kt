package ru.melowetty.tinkofftranslateservice.service.impl

import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import ru.melowetty.tinkofftranslateservice.entity.TranslateRecord
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.repository.TranslateRecordRepository
import ru.melowetty.tinkofftranslateservice.service.LanguageService
import ru.melowetty.tinkofftranslateservice.service.TranslateService
import ru.melowetty.tinkofftranslateservice.service.TranslatorService
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.regex.Pattern

@Service
class TranslateServiceImpl(
    private val translatorService: TranslatorService,
    private val translateRecordRepository: TranslateRecordRepository,
    private val languageService: LanguageService
): TranslateService {
    override fun translate(text: String, sourceLanguage: String, targetLanguage: String, ip: String): String {
        val source = languageService.getLanguageByCode(sourceLanguage)
            ?: throw IllegalArgumentException("Не найден исходный язык")

        val target = languageService.getLanguageByCode(targetLanguage)
            ?: throw IllegalArgumentException("Не найден требуемый язык")

        if(text.isBlank()) {
            throw IllegalArgumentException("Текст для перевода не может быть пустым")
        }
        
        val translated = translateText(text, source, target)

        val record = TranslateRecord(
            sourceLanguage = source,
            targetLanguage = target,
            input = text,
            translated = translated,
            createdOn = LocalDateTime.now(),
            id = 0,
            ipAddress = ip,
        )

        CoroutineScope(Dispatchers.IO).launch {
            translateRecordRepository.saveTranslateRecord(record)
        }

        return translated
    }

    private fun translateText(text: String, sourceLanguage: Language, targetLanguage: Language): String {
        return runBlocking {
            val executor = Executors.newFixedThreadPool(10)
            val dispatcher = executor.asCoroutineDispatcher()
            val scope = CoroutineScope(dispatcher)

            val parts = REGEX_FOR_PARTS.findAll(text).map { it.value }.toList()

            val processedParts = parts.map {
                return@map scope.async {
                    if(!REGEX_SYMBOLS.matches(it)) {
                        translatorService.translateWord(it, sourceLanguage, targetLanguage)
                    }
                    else " $it"
                }
            }.awaitAll()
            executor.shutdown()

            processedParts

        }.joinToString(" ").replace("  ", "").trim()
    }

    companion object {
        val REGEX_FOR_PARTS = Pattern.compile("\\w+|[^\\w\\s]+", Pattern.UNICODE_CHARACTER_CLASS).toRegex()
        val REGEX_SYMBOLS = Pattern.compile("[^\\w\\s]+", Pattern.UNICODE_CHARACTER_CLASS).toRegex()
    }
}