package com.afrosin.dictionary.view

import androidx.appcompat.app.AppCompatActivity
import com.afrosin.dictionary.R
import com.afrosin.dictionary.interactor.IInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.utils.ui.AlertDialogFragment
import com.afrosin.dictionary.viewmodel.BaseViewModel


abstract class BaseActivity<T : AppState, I : IInteractor<T>> : AppCompatActivity() {

    abstract val activityViewModel: BaseViewModel<T>

    abstract fun renderData(appState: T)

    protected var isNetworkAvailable: Boolean = false

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "COM_AFROSIN_DICTIONARY_DIALOG_FRAGMENT_TAG"
    }
}
