package com.afrosin.core

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afrosin.core.viewmodels.BaseViewModel
import com.afrosin.core.viewmodels.IInteractor
import com.afrosin.model.data.AppState
import com.afrosin.model.data.DataModel
import com.afrosin.utils.network.OnlineLiveData
import com.afrosin.utils.ui.AlertDialogFragment


abstract class BaseActivity<T : AppState, I : IInteractor<T>> : AppCompatActivity() {

    abstract val activityViewModel: BaseViewModel<T>
    protected abstract val layoutRes: Int
    protected var isNetworkAvailable: Boolean = true

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(
            this@BaseActivity,
            {
                isNetworkAvailable = it
                if (!isNetworkAvailable) {
                    Toast.makeText(
                        this@BaseActivity,
                        R.string.dialog_message_device_is_offline,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
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
                showViewLoading(appState.progress)
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_textview_stub), appState.error.message)
            }
        }
    }


    abstract fun showViewLoading(progress: Int?)

    abstract fun showViewWorking()

}
