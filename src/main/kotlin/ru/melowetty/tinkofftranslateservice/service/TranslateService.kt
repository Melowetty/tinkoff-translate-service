package ru.melowetty.tinkofftranslateservice.service

interface TranslateService {
    fun translate(text: String, sourceLanguage: String, targetLanguage: String, ip: String): String
}