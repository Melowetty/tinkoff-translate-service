package ru.melowetty.tinkofftranslateservice.controller.request

data class TranslateRequest(
    val sourceLanguage: String,
    val targetLanguage: String,
    val text: String,
)
