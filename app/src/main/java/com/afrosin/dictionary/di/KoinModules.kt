package com.afrosin.dictionary.di

import androidx.room.Room
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.view.MainActivity
import com.afrosin.dictionary.viewmodels.MainViewModel
import com.afrosin.model.data.dto.DataModelDto
import com.afrosin.repository.*
import com.afrosin.repository.room.HistoryDataBase
import com.afrosin.repository.room.RoomDataBaseImplementation
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {
    single {
        Room.databaseBuilder(
            get(),
            HistoryDataBase::class.java,
            "HistoryDB"
        ).build()
    }
    single { get<HistoryDataBase>().historyDao() }
    single<IRepository<List<DataModelDto>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<IRepositoryLocal<List<DataModelDto>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(
                get()
            )
        )
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}