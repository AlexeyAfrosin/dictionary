package com.afrosin.dictionary.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityMainBinding
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.view.adapter.MainAdapter
import com.afrosin.dictionary.viewmodel.MainViewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private val vb: ActivityMainBinding by viewBinding()
    private var adapter: MainAdapter? = null

    override val activityViewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vb.mainActivityRecyclerview) {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter =
                MainAdapter(onListItemClickListener, null)

        }

        vb.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    activityViewModel.getData(searchWord, true).observe(this@MainActivity, observer)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
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
                activityViewModel.getData("hi", true).observe(this@MainActivity, observer)
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