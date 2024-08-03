package ru.melowetty.tinkofftranslateservice.mapper

import org.springframework.stereotype.Component
import ru.melowetty.tinkofftranslateservice.dto.LanguageDto
import ru.melowetty.tinkofftranslateservice.entity.Language

@Component
class LanguageMapper {
    fun toDto(language: Language): LanguageDto {
        return LanguageDto(
            code = language.code,
            name = language.name,
        )
    }
}