package com.afrosin.dictionary.viewmodel

import androidx.lifecycle.LiveData
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.dataSource.DataSourceLocal
import com.afrosin.dictionary.model.dataSource.DataSourceRemote
import com.afrosin.dictionary.repository.RepositoryImplementation
import io.reactivex.rxjava3.observers.DisposableObserver

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }

                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
