package ru.melowetty.tinkofftranslateservice.service.impl

import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import ru.melowetty.tinkofftranslateservice.model.Language
import ru.melowetty.tinkofftranslateservice.service.TranslateService
import ru.melowetty.tinkofftranslateservice.service.TranslatorService
import java.util.concurrent.Executors

@Service
class TranslateServiceImpl(
    private val translatorService: TranslatorService,
): TranslateService {
    override fun translate(text: String, source: Language, target: Language, ip: String): String {
        val words = runBlocking {
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
        }
        return words.joinToString(" ")
    }
}