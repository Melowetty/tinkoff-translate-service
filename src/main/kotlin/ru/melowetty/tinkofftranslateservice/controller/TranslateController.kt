package ru.melowetty.tinkofftranslateservice.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.service.TranslateService

@RestController
@RequestMapping("/translate")
class TranslateController(
    private val translateService: TranslateService
) {
    @PostMapping
    fun translate(webRequest: HttpServletRequest, @RequestBody request: TranslateRequest): TranslateResponse {
        val source = Language.getByCode(request.sourceLanguage)
            ?: throw IllegalArgumentException("Не найден исходный язык")

        val target = Language.getByCode(request.targetLanguage)
            ?: throw IllegalArgumentException("Не найден требуемый язык")

        if(request.text.isBlank()) {
            throw IllegalArgumentException("Текст для перевода не может быть пустым")
        }

        val ip = webRequest.remoteAddr

        val translation = translateService.translate(text = request.text, targetLanguage = target, sourceLanguage = source, ip = ip)

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