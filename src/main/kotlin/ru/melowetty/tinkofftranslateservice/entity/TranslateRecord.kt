package ru.melowetty.tinkofftranslateservice.entity

import java.time.LocalDateTime

data class TranslateRecord(
    val id: Int,
    val ipAddress: String,
    val sourceLanguage: Language,
    val targetLanguage: Language,
    val input: String,
    val translated: String,
    val createdOn: LocalDateTime,
)
