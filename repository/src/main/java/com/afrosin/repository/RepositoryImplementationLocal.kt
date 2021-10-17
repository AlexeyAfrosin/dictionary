package com.afrosin.repository

import com.afrosin.model.data.AppState
import com.afrosin.model.data.dto.DataModelDto

class RepositoryImplementationLocal(private val dataSource: IDataSourceLocal<List<DataModelDto>>) :
    IRepositoryLocal<List<DataModelDto>> {
    override suspend fun saveToDB(appState: AppState) {
        return dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<DataModelDto> {
        return dataSource.getData(word)
    }
}

