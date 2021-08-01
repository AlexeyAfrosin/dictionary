package com.afrosin.dictionary.repository

import io.reactivex.rxjava3.core.Observable

interface IRepository<T> {

    fun getData(word: String): Observable<T>
}