package ru.melowetty.tinkofftranslateservice.repository.impl

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.melowetty.tinkofftranslateservice.entity.TranslateRecord
import ru.melowetty.tinkofftranslateservice.repository.TranslateRecordRepository
import java.sql.Statement
import java.sql.Timestamp

@Repository
class TranslateRecordRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate
): TranslateRecordRepository {
    override fun saveTranslateRecord(record: TranslateRecord): TranslateRecord {
        val sql = "INSERT INTO translate_record " +
        "(ip_address, source_language, target_language, input, translated, created_on) " +
                "VALUES (?, ?, ?, ?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).apply {
                setString(1, record.ipAddress)
                setInt(2, record.sourceLanguage.id)
                setInt(3, record.targetLanguage.id)
                setString(4, record.input)
                setString(5, record.translated)
                setTimestamp(6, Timestamp.valueOf(record.createdOn))
            }
        }, keyHolder)

        val keys = keyHolder.keys ?:
        throw RuntimeException("Попытка создать язык была неуспешной!")

        val id = keys.get("id") as Int? ?:
            throw RuntimeException("Попытка создать запись перевода была неуспешной!")

        return record.copy(id = id)
    }
}