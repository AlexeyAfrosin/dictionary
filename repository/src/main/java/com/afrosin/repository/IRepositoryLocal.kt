package com.afrosin.repository

import com.afrosin.model.data.AppState

interface IRepositoryLocal<T> : IRepository<T> {

    suspend fun saveToDB(appState: AppState)
}