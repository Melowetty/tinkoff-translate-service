package ru.melowetty.tinkofftranslateservice.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.tinkofftranslateservice.service.TranslateService

@RestController
@RequestMapping("/translate")
class TranslateController(
    private val translateService: TranslateService
) {
    @PostMapping
    fun translate(webRequest: HttpServletRequest, @RequestBody request: TranslateRequest): TranslateResponse {
        if(request.text.isBlank()) {
            throw IllegalArgumentException("Текст для перевода не может быть пустым")
        }

        val ip = webRequest.remoteAddr

        val translation = translateService.translate(text = request.text,
            targetLanguage = request.targetLanguage,
            sourceLanguage = request.sourceLanguage, ip = ip)

        return TranslateResponse(
            translated = translation,
        )
    }

    data class TranslateResponse(
        val translated: String,
    )

    data class TranslateRequest(
        val sourceLanguage: String,
        val targetLanguage: String,
        val text: String,
    )
}