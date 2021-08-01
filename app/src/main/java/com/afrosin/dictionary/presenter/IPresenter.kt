package com.afrosin.dictionary.presenter

import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.view.IView

interface IPresenter<T : AppState, V : IView> {

    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}