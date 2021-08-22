package com.afrosin.historyscreen.view.history

import com.afrosin.core.viewmodels.IInteractor
import com.afrosin.dictionary.viewmodels.mapSearchResultToResult
import com.afrosin.model.data.AppState
import com.afrosin.model.data.dto.DataModelDto
import com.afrosin.repository.IRepository
import com.afrosin.repository.IRepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: IRepository<List<DataModelDto>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModelDto>>
) : IInteractor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(mapSearchResultToResult(repositoryRemote.getData(word)))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(mapSearchResultToResult(repositoryLocal.getData(word)))
        }
        return appState
    }
}
