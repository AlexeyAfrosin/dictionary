package com.afrosin.dictionary.viewmodels

import com.afrosin.model.data.Meaning
import com.afrosin.model.data.Translation
import com.afrosin.model.data.dto.DataModelDto
import com.afrosin.model.data.dto.MeaningsDto
import com.afrosin.model.data.dto.TranslationDto
import org.junit.Assert.*
import org.junit.Test

class SearchResultParserKtTest {

    @Test
    fun convertMeaningsToString_EmptyArray_Equal() {
        val meanings = listOf<Meaning>();
        assertEquals("", convertMeaningsToString(meanings));
    }

    @Test
    fun convertMeaningsToString_OneElement_Equal() {
        val meanings = listOf(Meaning(Translation("one")));
        assertEquals("one", convertMeaningsToString(meanings));
    }

    @Test
    fun convertMeaningsToString_TwoElement_Equal() {
        val meanings = listOf(
            Meaning(Translation("one")),
            Meaning(Translation("two"))
        );
        assertEquals("one, two", convertMeaningsToString(meanings));
    }

    @Test
    fun convertMeaningsToString_TwoElement_NotEquals() {
        val meanings = listOf(
            Meaning(Translation("one")),
            Meaning(Translation("two"))
        );
        assertNotEquals("one, two, ", convertMeaningsToString(meanings));
    }

    @Test
    fun mapSearchResultToResult_ArrayEquals() {
        val dataModelDtoList = listOf(
            DataModelDto(
                "text one", listOf(
                    MeaningsDto(TranslationDto("meaning one"), ""),
                    MeaningsDto(TranslationDto("meaning two"), ""),
                )
            )
        )
        val expectedArray = arrayOf(
            "meaning one",
            "meaning two"
        )

        val actualArray = arrayOfNulls<String>(2);

        mapSearchResultToResult(dataModelDtoList)[0].meanings.forEachIndexed { index, meaning ->
            actualArray[index] = meaning.translation.translation
        }

        assertArrayEquals(expectedArray, actualArray)
    }
}
