package com.afrosin.dictionary.interactor

import com.afrosin.core.viewmodels.IInteractor
import com.afrosin.model.data.AppState
import com.afrosin.model.data.DataModel
import com.afrosin.repository.IRepository
import com.afrosin.repository.IRepositoryLocal

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModel>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}