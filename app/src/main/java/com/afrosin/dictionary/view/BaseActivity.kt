package com.afrosin.dictionary.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.LoadingLayoutBinding
import com.afrosin.dictionary.interactor.IInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.utils.ui.AlertDialogFragment
import com.afrosin.dictionary.viewmodels.BaseViewModel


abstract class BaseActivity<T : AppState, I : IInteractor<T>> : AppCompatActivity() {

    private val vb: LoadingLayoutBinding by viewBinding()

    abstract val activityViewModel: BaseViewModel<T>

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

    abstract fun setDataToAdapter(data: List<DataModel>)

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                with(vb) {
                    if (appState.progress != null) {
                        progressBarHorizontal.visibility = View.VISIBLE
                        progressBarRound.visibility = View.GONE
                        progressBarHorizontal.progress = appState.progress

                    } else {
                        progressBarHorizontal.visibility = View.GONE
                        progressBarRound.visibility = View.VISIBLE
                    }
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_textview_stub), appState.error.message)
            }
        }
    }

    private fun showViewLoading() {
        vb.loadingFrameLayout.visibility = View.VISIBLE
    }

    private fun showViewWorking() {
        vb.loadingFrameLayout.visibility = View.GONE
    }

}
