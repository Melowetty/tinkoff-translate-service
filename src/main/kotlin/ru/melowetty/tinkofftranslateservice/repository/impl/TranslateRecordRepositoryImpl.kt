package ru.melowetty.tinkofftranslateservice.repository.impl

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.melowetty.tinkofftranslateservice.entity.TranslateRecord
import ru.melowetty.tinkofftranslateservice.repository.TranslateRecordRepository
import java.sql.Timestamp

@Repository
class TranslateRecordRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate
): TranslateRecordRepository {
    override fun saveTranslateRecord(record: TranslateRecord): TranslateRecord {
        val id = jdbcTemplate.update("INSERT INTO translate_record " +
                "(ip_address, source_language, target_language, input, translated, created_on) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
            record.ipAddress,
            record.sourceLanguage.id,
            record.targetLanguage.id,
            record.input,
            record.translated,
            Timestamp.valueOf(record.createdOn))
        return record.copy(id = id)
    }
}