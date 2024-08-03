package ru.melowetty.tinkofftranslateservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.tinkofftranslateservice.dto.LanguageDto
import ru.melowetty.tinkofftranslateservice.mapper.LanguageMapper
import ru.melowetty.tinkofftranslateservice.service.LanguageService

@RestController
@RequestMapping("/languages")
class LanguageController(
    private val languageService: LanguageService,
    private val languageMapper: LanguageMapper
) {
    @GetMapping
    fun getAllLanguages(): List<LanguageDto> {
        return languageService.getLanguages().map { languageMapper.toDto(it) }
    }
}