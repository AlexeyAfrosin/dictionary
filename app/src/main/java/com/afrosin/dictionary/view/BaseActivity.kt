package com.afrosin.dictionary.view

import androidx.appcompat.app.AppCompatActivity
import com.afrosin.dictionary.interactor.IInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.viewmodel.BaseViewModel


abstract class BaseActivity<T : AppState, I : IInteractor<T>> : AppCompatActivity() {

    abstract val activityViewModel: BaseViewModel<T>

    abstract fun renderData(appState: T)
}
