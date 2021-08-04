package com.afrosin.dictionary.interactor

import io.reactivex.rxjava3.core.Observable

interface IInteractor<T> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}