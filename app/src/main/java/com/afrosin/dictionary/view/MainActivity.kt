package com.afrosin.dictionary.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityMainBinding
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.utils.convertMeaningsToString
import com.afrosin.dictionary.utils.network.isOnline
import com.afrosin.dictionary.view.adapter.MainAdapter
import com.afrosin.dictionary.viewmodels.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private val vb: ActivityMainBinding by viewBinding()
    private var adapter: MainAdapter? = null

    override lateinit var activityViewModel: MainViewModel

    private val observer = Observer<AppState> { renderData(it) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    WordDescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings[0].imageUrl
                    )
                )
            }
        }


    private val searchDialogFragmentOnSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    activityViewModel.getData(searchWord, true)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    private val searchFabClickListener: View.OnClickListener = View.OnClickListener {
        val searchDialogFragment = SearchDialogFragment.newInstance()

        searchDialogFragment.setOnSearchClickListener(searchDialogFragmentOnSearchClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniViewModel()
        initViews()

    }

    private fun initViews() {
        with(vb.mainActivityRecyclerview) {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter =
                MainAdapter(onListItemClickListener, null)

        }

        vb.searchFab.setOnClickListener(searchFabClickListener)
    }

    private fun iniViewModel() {
        check(vb.mainActivityRecyclerview.adapter == null) { "The ViewModel should be initialised first" }
        val mainViewModel: MainViewModel by viewModel()
        activityViewModel = mainViewModel
        activityViewModel.subscribe().observe(this@MainActivity, observer)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        with(vb.mainActivityRecyclerview) {
                            layoutManager = LinearLayoutManager(applicationContext)
                            adapter =
                                MainAdapter(onListItemClickListener, dataModel)

                        }
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    with(vb) {
                        progressBarHorizontal.visibility = VISIBLE
                        progressBarRound.visibility = GONE
                        progressBarHorizontal.progress = appState.progress
                    }
                } else {
                    with(vb) {
                        progressBarHorizontal.visibility = GONE
                        progressBarRound.visibility = VISIBLE
                    }
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        with(vb) {
            errorTextview.text = error ?: getString(R.string.undefined_error)
            reloadButton.setOnClickListener {
                activityViewModel.getData("hi", true)
            }
        }
    }

    private fun showViewSuccess() {
        with(vb) {
            successLinearLayout.visibility = VISIBLE
            loadingFrameLayout.visibility = GONE
            errorLinearLayout.visibility = GONE
        }

    }

    private fun showViewLoading() {
        with(vb) {
            successLinearLayout.visibility = GONE
            loadingFrameLayout.visibility = VISIBLE
            errorLinearLayout.visibility = GONE
        }
    }

    private fun showViewError() {
        with(vb) {
            successLinearLayout.visibility = GONE
            loadingFrameLayout.visibility = GONE
            errorLinearLayout.visibility = VISIBLE
        }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
    }

}