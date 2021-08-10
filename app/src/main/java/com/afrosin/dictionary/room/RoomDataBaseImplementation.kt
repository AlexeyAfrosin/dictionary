package com.afrosin.dictionary.room

import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSource

class RoomDataBaseImplementation : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented")
    }
}
