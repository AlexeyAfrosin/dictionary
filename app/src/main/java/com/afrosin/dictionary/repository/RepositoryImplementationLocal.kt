package com.afrosin.dictionary.repository

import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSourceLocal

class RepositoryImplementationLocal(private val dataSource: IDataSourceLocal<List<DataModel>>) :
    IRepositoryLocal<List<DataModel>> {
    override suspend fun saveToDB(appState: AppState) {
        return dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}

