package ru.melowetty.tinkofftranslateservice.service.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.service.TranslatorService

@Service
@Primary
class YandexTranslatorService(
    private val restTemplate: RestTemplate,
    private val environment: Environment
): TranslatorService {
    private val yandexTranslateApiKey: String = environment["api.yandex-translate.api-key"] ?:
        throw IllegalArgumentException("Yandex Translate API Key needs to be set")

    override suspend fun translateWord(word: String, sourceLanguage: Language, targetLanguage: Language): String {
        return withContext(Dispatchers.IO) {
            val body: Map<String, Any> =
                mapOf("targetLanguageCode" to targetLanguage.code,
                    "sourceLanguageCode" to sourceLanguage.code,
                    "texts" to arrayOf(word))
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            headers.set("Authorization", "Api-Key $yandexTranslateApiKey")
            val httpEntity = HttpEntity(body, headers)

            val res = restTemplate.exchange("$API_URL/translate", HttpMethod.POST, httpEntity, YandexApiTranslationsResponse::class.java).body!!
            res.translations.first().text
        }
    }
    companion object {
        private const val API_URL = "https://translate.api.cloud.yandex.net/translate/v2"
    }

    data class YandexApiTranslationsResponse(
        val translations: List<YandexApiTranslateResponse>,
    )

    data class YandexApiTranslateResponse(
        val text: String,
    )
}