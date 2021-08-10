package com.afrosin.dictionary.repository

import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSource

class RepositoryImplementation(private val dataSource: IDataSource<List<DataModel>>) :
    IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}

