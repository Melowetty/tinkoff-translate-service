package ru.melowetty.tinkofftranslateservice.repository

import ru.melowetty.tinkofftranslateservice.entity.TranslateRecord

interface TranslateRecordRepository {
    fun saveTranslateRecord(record: TranslateRecord): TranslateRecord
}