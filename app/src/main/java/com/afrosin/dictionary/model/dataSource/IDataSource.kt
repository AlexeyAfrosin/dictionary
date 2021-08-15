package com.afrosin.dictionary.model.dataSource

interface IDataSource<T> {

    suspend fun getData(word: String): T
}