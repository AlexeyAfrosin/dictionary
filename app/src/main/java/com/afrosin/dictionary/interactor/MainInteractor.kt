package com.afrosin.dictionary.interactor

import com.afrosin.dictionary.di.NAME_LOCAL
import com.afrosin.dictionary.di.NAME_REMOTE
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.repository.IRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: IRepository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: IRepository<List<DataModel>>,
) : IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote
        } else {
            repositoryLocal
        }.getData(word).map { AppState.Success(it) }
    }
}
