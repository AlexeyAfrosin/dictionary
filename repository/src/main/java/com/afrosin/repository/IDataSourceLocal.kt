package com.afrosin.repository

import com.afrosin.model.data.AppState

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDB(appState: AppState)
}