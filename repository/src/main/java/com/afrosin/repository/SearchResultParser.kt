package com.afrosin.repository

import com.afrosin.model.data.AppState
import com.afrosin.model.data.dto.DataModelDto
import com.afrosin.repository.room.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModelDto> {
    val searchResult = ArrayList<DataModelDto>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModelDto(entity.word, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text, null)
            }
        }
        else -> null
    }
}