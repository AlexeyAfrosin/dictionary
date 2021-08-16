package com.afrosin.dictionary.room

import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSourceLocal
import com.afrosin.dictionary.utils.convertDataModelSuccessToEntity
import com.afrosin.dictionary.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    IDataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
