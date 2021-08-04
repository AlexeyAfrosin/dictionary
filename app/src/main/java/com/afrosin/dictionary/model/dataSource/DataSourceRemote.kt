package com.afrosin.dictionary.model.dataSource

import com.afrosin.dictionary.model.api.RetrofitImplementation
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.room.RoomDataBaseImplementation
import io.reactivex.rxjava3.core.Observable

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}