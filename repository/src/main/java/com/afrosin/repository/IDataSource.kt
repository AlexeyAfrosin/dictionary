package com.afrosin.repository

interface IDataSource<T> {

    suspend fun getData(word: String): T
}