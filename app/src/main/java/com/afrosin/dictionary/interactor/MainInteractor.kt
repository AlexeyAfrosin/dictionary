package com.afrosin.dictionary.interactor

import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.repository.IRepository
import io.reactivex.rxjava3.core.Observable

class MainInteractor(
    private val remoteRepository: IRepository<List<DataModel>>,
    private val localRepository: IRepository<List<DataModel>>
) : IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
