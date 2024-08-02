package ru.melowetty.tinkofftranslateservice.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LanguageTest {
    @Test
    fun `test language by code in upper case`() {
        val expected = Language.RUSSIAN

        val actual = Language.getByCode("RU")

        assertEquals(expected, actual)
    }

    @Test
    fun `test language by code in normal case`() {
        val expected = Language.RUSSIAN

        val actual = Language.getByCode("ru")

        assertEquals(expected, actual)
    }

    @Test
    fun `test non exists language by code`() {
        val actual = Language.getByCode("non-exist")

        assertNull(actual)
    }
}