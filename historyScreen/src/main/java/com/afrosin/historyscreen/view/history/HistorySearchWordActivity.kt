package com.afrosin.historyscreen.view.history

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.core.BaseActivity
import com.afrosin.historyscreen.R
import com.afrosin.historyscreen.databinding.ActivityHistorySearchWordBinding
import com.afrosin.historyscreen.di.injectDependencies
import com.afrosin.model.data.AppState
import com.afrosin.model.data.DataModel
import org.koin.android.scope.currentScope

class HistorySearchWordActivity : BaseActivity<AppState, HistoryInteractor>() {

    override val layoutRes = R.layout.activity_history_search_word
    private val vb: ActivityHistorySearchWordBinding by viewBinding()
    override lateinit var activityViewModel: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (vb.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        injectDependencies()
        val vm: HistoryViewModel by currentScope.inject()
        activityViewModel = vm
        activityViewModel.subscribe()
            .observe(this@HistorySearchWordActivity, { renderData(it) })
    }

    private fun initViews() {
        vb.historyActivityRecyclerview.adapter = adapter
    }

    override fun showViewLoading(progress: Int?) {
        with(vb.historySearchWordLoadingLayout) {
            historyLoadingFrameLayout.visibility = View.VISIBLE

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
        vb.historySearchWordLoadingLayout.historyLoadingFrameLayout.visibility = View.GONE
    }
}

