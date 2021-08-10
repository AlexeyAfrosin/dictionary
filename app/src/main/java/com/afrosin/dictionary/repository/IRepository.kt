package com.afrosin.dictionary.repository

interface IRepository<T> {

    suspend fun getData(word: String): T
}