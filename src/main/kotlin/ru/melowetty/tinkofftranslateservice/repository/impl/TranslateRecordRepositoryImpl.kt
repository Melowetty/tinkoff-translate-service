package ru.melowetty.tinkofftranslateservice.repository.impl

import org.springframework.stereotype.Repository
import ru.melowetty.tinkofftranslateservice.entity.TranslateRecord
import ru.melowetty.tinkofftranslateservice.repository.TranslateRecordRepository

@Repository
class TranslateRecordRepositoryImpl: TranslateRecordRepository {
    override fun saveTranslateRecord(record: TranslateRecord): TranslateRecord {
        return record.copy(id = 1)
    }
}