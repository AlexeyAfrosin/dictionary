package com.afrosin.repository.room

import com.afrosin.model.data.AppState
import com.afrosin.model.data.dto.DataModelDto
import com.afrosin.repository.IDataSourceLocal
import com.afrosin.repository.convertDataModelSuccessToEntity
import com.afrosin.repository.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    IDataSourceLocal<List<DataModelDto>> {

    override suspend fun getData(word: String): List<DataModelDto> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
