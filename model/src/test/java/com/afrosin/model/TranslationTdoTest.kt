package com.afrosin.model

import com.afrosin.model.data.dto.TranslationDto
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TranslationTdoTest {
    @Test
    fun translation_isNull() {
        assertNull(TranslationDto(null).translation)
    }

    @Test
    fun translation_isNotNull() {
        assertNotNull(TranslationDto("one").translation)
    }

    @Test
    fun translation_isSame() {
        val one = TranslationDto("one")
        val oneSecond = one
        assertSame(one, oneSecond)
    }
}