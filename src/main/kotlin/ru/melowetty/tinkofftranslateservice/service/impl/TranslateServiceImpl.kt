package ru.melowetty.tinkofftranslateservice.service.impl

import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import ru.melowetty.tinkofftranslateservice.entity.TranslateRecord
import ru.melowetty.tinkofftranslateservice.model.Language
import ru.melowetty.tinkofftranslateservice.repository.TranslateRecordRepository
import ru.melowetty.tinkofftranslateservice.service.TranslateService
import ru.melowetty.tinkofftranslateservice.service.TranslatorService
import java.time.LocalDateTime
import java.util.concurrent.Executors

@Service
class TranslateServiceImpl(
    private val translatorService: TranslatorService,
    private val translateRecordRepository: TranslateRecordRepository
): TranslateService {
    override fun translate(text: String, source: Language, target: Language, ip: String): String {
        val translated = translateText(text, source, target)

        val record = TranslateRecord(
            source = source,
            target = target,
            input = text,
            translated = translated,
            time = LocalDateTime.now(),
            id = 0,
            ipAddress = ip,
        )

        CoroutineScope(Dispatchers.IO).launch {
            translateRecordRepository.saveTranslateRecord(record)
        }

        return translated
    }

    private fun translateText(text: String, source: Language, target: Language): String {
        return runBlocking {
            val executor = Executors.newFixedThreadPool(10)
            val dispatcher = executor.asCoroutineDispatcher()
            val scope = CoroutineScope(dispatcher)

            val words = text.split(" ").map {
                scope.async {
                    translatorService.translateWord(it, source, target)
                }
            }.awaitAll()
            executor.shutdown()

            words

        }.joinToString(" ")
    }
}