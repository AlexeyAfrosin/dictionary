package com.afrosin.dictionary.model.dataSource

import io.reactivex.rxjava3.core.Observable

interface IDataSource<T> {

    fun getData(word: String): Observable<T>
}