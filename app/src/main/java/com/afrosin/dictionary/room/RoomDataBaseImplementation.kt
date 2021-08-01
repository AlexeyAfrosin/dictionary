package com.afrosin.dictionary.room

import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSource
import io.reactivex.rxjava3.core.Observable


class RoomDataBaseImplementation : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}
