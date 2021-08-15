package com.afrosin.dictionary.interactor

interface IInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}