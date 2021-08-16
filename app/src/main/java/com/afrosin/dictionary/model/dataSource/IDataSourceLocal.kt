package com.afrosin.dictionary.model.dataSource

import com.afrosin.dictionary.model.data.AppState

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDB(appState: AppState)
}