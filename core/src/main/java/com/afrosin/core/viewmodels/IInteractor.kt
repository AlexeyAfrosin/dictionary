package com.afrosin.core.viewmodels

interface IInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}