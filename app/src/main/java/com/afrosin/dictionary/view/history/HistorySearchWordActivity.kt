package com.afrosin.dictionary.view.history

import android.os.Bundle
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityHistorySearchWordBinding
import com.afrosin.dictionary.interactor.HistoryInteractor
import com.afrosin.dictionary.model.data.AppState
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.view.BaseActivity
import com.afrosin.dictionary.viewmodels.HistoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HistorySearchWordActivity : BaseActivity<AppState, HistoryInteractor>() {

    private val vb: ActivityHistorySearchWordBinding by viewBinding()
    override lateinit var activityViewModel: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_search_word)
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
        val vm: HistoryViewModel by viewModel()
        activityViewModel = vm
        activityViewModel.subscribe()
            .observe(this@HistorySearchWordActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        vb.historyActivityRecyclerview.adapter = adapter
    }
}

