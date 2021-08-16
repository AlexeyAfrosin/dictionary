package com.afrosin.dictionary.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityMainBinding
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.utils.convertMeaningsToString
import com.afrosin.dictionary.utils.network.isOnline
import com.afrosin.dictionary.view.adapter.MainAdapter
import com.afrosin.dictionary.view.history.HistorySearchWordActivity
import com.afrosin.dictionary.viewmodels.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private val vb: ActivityMainBinding by viewBinding()
    override lateinit var activityViewModel: MainViewModel

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val searchFabClickListener: View.OnClickListener = View.OnClickListener {
        val searchDialogFragment = SearchDialogFragment.newInstance()

        searchDialogFragment.setOnSearchClickListener(searchDialogFragmentOnSearchClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }

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

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private val searchDialogFragmentOnSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    activityViewModel.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniViewModel()
        initViews()

    }

    private fun initViews() {
        with(vb) {
            mainActivityRecyclerview.adapter = adapter
            searchFab.setOnClickListener(searchFabClickListener)
        }
    }

    private fun iniViewModel() {
        check(vb.mainActivityRecyclerview.adapter == null) { "The mainViewModel should be initialised first" }
        val mainViewModel: MainViewModel by viewModel()
        activityViewModel = mainViewModel
        activityViewModel.subscribe().observe(this@MainActivity, { renderData(it) })
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistorySearchWordActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showViewLoading(progress: Int?) {
        with(vb.mainLoadingLayout) {
            loadingFrameLayout.visibility = View.VISIBLE

            if (progress != null) {
                progressBarHorizontal.visibility = View.VISIBLE
                progressBarRound.visibility = View.GONE
                progressBarHorizontal.progress = progress
            } else {
                progressBarHorizontal.visibility = View.GONE
                progressBarRound.visibility = View.VISIBLE
            }
        }
    }

    override fun showViewWorking() {
        vb.mainLoadingLayout.loadingFrameLayout.visibility = View.GONE
    }

}