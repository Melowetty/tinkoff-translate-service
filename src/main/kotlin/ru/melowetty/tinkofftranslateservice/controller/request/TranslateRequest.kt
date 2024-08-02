package ru.melowetty.tinkofftranslateservice.controller.request

data class TranslateRequest(
    val source: String,
    val target: String,
    val text: String,
)
