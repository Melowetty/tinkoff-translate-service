package ru.melowetty.tinkofftranslateservice.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.tinkofftranslateservice.controller.request.TranslateRequest
import ru.melowetty.tinkofftranslateservice.controller.response.TranslateResponse
import ru.melowetty.tinkofftranslateservice.model.Language
import ru.melowetty.tinkofftranslateservice.service.TranslateService

@RestController
@RequestMapping("/translate")
class TranslateController(
    private val translateService: TranslateService
) {
    @PostMapping
    fun translate(webRequest: HttpServletRequest, @RequestBody request: TranslateRequest): TranslateResponse {
        val source = Language.getByCode(request.source)
            ?: throw IllegalArgumentException("Не найден исходный язык")

        val target = Language.getByCode(request.target)
            ?: throw IllegalArgumentException("Не найден требуемый язык")

        if(request.text.isBlank()) {
            throw IllegalArgumentException("Текст для перевода не может быть пустым")
        }

        val ip = webRequest.remoteAddr

        val translation = translateService.translate(text = request.text, target = target, source = source, ip = ip)

        return TranslateResponse(
            translated = translation,
        )
    }
}