package com.afrosin.dictionary.di

import com.afrosin.historyscreen.view.history.HistoryInteractor
import com.afrosin.historyscreen.view.history.HistoryViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}