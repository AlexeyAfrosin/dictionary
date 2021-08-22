package com.afrosin.dictionary.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.core.BaseActivity
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityMainBinding
import com.afrosin.dictionary.di.injectDependencies
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.view.adapter.MainAdapter
import com.afrosin.dictionary.viewmodels.MainViewModel
import com.afrosin.dictionary.viewmodels.convertMeaningsToString
import com.afrosin.model.data.AppState
import com.afrosin.model.data.DataModel
import com.afrosin.utils.viewById
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.scope.currentScope

private const val HISTORY_SEARCH_ACTIVITY_PATH =
    "com.afrosin.historyscreen.view.history.HistorySearchWordActivity"
private const val HISTORY_SEARCH_ACTIVITY_FEATURE_NAME = "historyScreen"
private const val UPDATE_REQUEST_CODE = 42

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    override val layoutRes = R.layout.activity_main
    private val vb: ActivityMainBinding by viewBinding()
    override lateinit var activityViewModel: MainViewModel

    private lateinit var splitInstallManager: SplitInstallManager

    private lateinit var appUpdateManager: AppUpdateManager

    private val mainActivityRecyclerview by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFab by viewById<FloatingActionButton>(R.id.search_fab)


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
                        data.meanings!![0].imageUrl
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
                if (isNetworkAvailable) {
                    activityViewModel.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniViewModel()
        initViews()
        checkForUpdates()

    }

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        val appUpdateInfo = appUpdateManager.appUpdateInfo

        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdateListener)

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    IMMEDIATE,
                    this,
                    UPDATE_REQUEST_CODE
                )
            }
        }


    }

    private val stateUpdateListener: InstallStateUpdatedListener =
        object : InstallStateUpdatedListener {
            override fun onStateUpdate(state: InstallState) {
                state.let {
                    if (it.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackBarForCompleteUpdate()
                    }
                }
            }
        }

    private fun popupSnackBarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.activity_main_layout),
            "Обновление готово к установке",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    private fun initViews() {
        mainActivityRecyclerview.adapter = adapter
        searchFab.setOnClickListener(searchFabClickListener)
    }

    private fun iniViewModel() {
        check(mainActivityRecyclerview.adapter == null) { "The mainViewModel should be initialised first" }
        injectDependencies()
        val mainViewModel: MainViewModel by currentScope.inject()
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
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request = SplitInstallRequest
                    .newBuilder()
                    .addModule(HISTORY_SEARCH_ACTIVITY_FEATURE_NAME)
                    .build()
                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent =
                            Intent().setClassName(packageName, HISTORY_SEARCH_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPDATE_REQUEST_CODE) {
            appUpdateManager.unregisterListener(stateUpdateListener)
        } else {
            Toast.makeText(
                applicationContext,
                "Update flow failed! Result code: $resultCode",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackBarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        UPDATE_REQUEST_CODE
                    )
                }
            }
    }
}