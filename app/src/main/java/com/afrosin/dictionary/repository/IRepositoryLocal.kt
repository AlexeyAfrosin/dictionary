package com.afrosin.dictionary.repository

import com.afrosin.dictionary.model.data.AppState

interface IRepositoryLocal<T> : IRepository<T> {

    suspend fun saveToDB(appState: AppState)
}