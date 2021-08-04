package com.afrosin.dictionary.view

import com.afrosin.dictionary.model.data.AppState

interface IView {
    fun renderData(appState: AppState)
}