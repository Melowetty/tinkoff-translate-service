package ru.melowetty.tinkofftranslateservice.repository.impl

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.melowetty.tinkofftranslateservice.entity.Language
import ru.melowetty.tinkofftranslateservice.repository.LanguageRepository
import java.sql.Statement

@Repository
class LanguageRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate
): LanguageRepository {
    override fun createLanguage(code: String, name: String): Language {
        val sql = "INSERT INTO language(code, name) VALUES(?, ?)"
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({
            val psmt = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            psmt.setString(1, code.lowercase())
            psmt.setString(2, name)
            psmt

        }, keyHolder)
        val id = keyHolder.key?.toInt() ?:
            throw RuntimeException("Попытка создать язык была неуспешной!")

        return Language(
            id = id,
            code = code,
            name = name,
        )
    }

    override fun getLanguageByCode(code: String): Language? {
        val sql = "SELECT * FROM language WHERE code = ?"
        return jdbcTemplate.queryForObject(sql, Language::class.java, code)
    }

    override fun deleteLanguageByCode(code: String) {
        val sql = "DELETE FROM language WHERE code = ?"
        jdbcTemplate.update(sql, code)
    }

    override fun getAllLanguages(): List<Language> {
        return jdbcTemplate.queryForList("SELECT * FROM language ORDER BY id DESC", Language::class.java)
    }
}