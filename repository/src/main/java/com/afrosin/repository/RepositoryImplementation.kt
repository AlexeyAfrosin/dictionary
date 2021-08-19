package com.afrosin.repository

import com.afrosin.model.data.DataModel

class RepositoryImplementation(private val dataSource: IDataSource<List<DataModel>>) :
    IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}

