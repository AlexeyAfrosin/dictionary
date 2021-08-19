package com.afrosin.repository

import com.afrosin.model.data.AppState
import com.afrosin.model.data.DataModel

class RepositoryImplementationLocal(private val dataSource: IDataSourceLocal<List<DataModel>>) :
    IRepositoryLocal<List<DataModel>> {
    override suspend fun saveToDB(appState: AppState) {
        return dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}

