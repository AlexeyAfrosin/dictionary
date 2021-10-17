package com.afrosin.historyscreen.di

import com.afrosin.historyscreen.view.history.HistoryInteractor
import com.afrosin.historyscreen.view.history.HistorySearchWordActivity
import com.afrosin.historyscreen.view.history.HistoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    scope(named<HistorySearchWordActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}