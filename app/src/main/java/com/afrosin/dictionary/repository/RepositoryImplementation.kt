package com.afrosin.dictionary.repository

import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSource
import io.reactivex.rxjava3.core.Observable


class RepositoryImplementation(private val dataSource: IDataSource<List<DataModel>>) :
    IRepository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}

