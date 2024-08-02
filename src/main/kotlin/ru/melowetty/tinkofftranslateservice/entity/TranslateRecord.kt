package ru.melowetty.tinkofftranslateservice.entity

import ru.melowetty.tinkofftranslateservice.model.Language
import java.time.LocalDateTime

data class TranslateRecord(
    private val id: Long,
    private val source: Language,
    private val target: Language,
    private val input: String,
    private val translated: String,
    private val time: LocalDateTime,
)
