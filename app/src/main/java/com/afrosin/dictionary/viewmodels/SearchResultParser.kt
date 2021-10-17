package com.afrosin.dictionary.viewmodels

import com.afrosin.model.data.AppState
import com.afrosin.model.data.DataModel
import com.afrosin.model.data.Meaning
import com.afrosin.model.data.Translation
import com.afrosin.model.data.dto.DataModelDto


fun parseOnlineSearchResults(state: AppState): AppState {
    return AppState.Success(mapResult(state, true))
}

private fun mapResult(
    data: AppState,
    isOnline: Boolean
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (data) {
        is AppState.Success -> {
            getSuccessResultData(data, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataModel>
) {
    val dataModels: List<DataModel> = data.data as List<DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(DataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (dataModel.text.isNotBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        for (meaning in dataModel.meanings) {
            if (meaning.translation.translation.isNotBlank()) {
                newMeanings.add(Meaning(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun convertMeaningsToString(meanings: List<Meaning>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation.translation, ", ")
        } else {
            meaning.translation.translation
        }
    }
    return meaningsSeparatedByComma
}

fun mapSearchResultToResult(searchResults: List<DataModelDto>): List<DataModel> {
    return searchResults.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDto ->
                Meaning(
                    Translation(meaningsDto?.translation?.translation ?: ""),
                    meaningsDto?.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.text ?: "", meanings)
    }
}