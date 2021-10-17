package com.afrosin.repository

interface IRepository<T> {

    suspend fun getData(word: String): T
}