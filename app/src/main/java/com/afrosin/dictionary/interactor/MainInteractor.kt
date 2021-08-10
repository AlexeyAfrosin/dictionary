package com.afrosin.dictionary.interactor

import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.repository.IRepository

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>,
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}