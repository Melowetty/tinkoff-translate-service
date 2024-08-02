package ru.melowetty.tinkofftranslateservice.model

enum class Language(
    val code: String
) {
    RUSSIAN("ru"),
    ENGLISH("en");

    companion object {
        fun getByCode(code: String): Language? {
            val lowerCode = code.lowercase()
            return entries.find { it.code.lowercase() == lowerCode }
        }
    }
}