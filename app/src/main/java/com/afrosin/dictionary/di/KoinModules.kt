package com.afrosin.dictionary.di

import androidx.room.Room
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.viewmodels.MainViewModel
import com.afrosin.historyscreen.view.history.HistoryInteractor
import com.afrosin.historyscreen.view.history.HistoryViewModel
import com.afrosin.model.data.DataModel
import com.afrosin.repository.*
import com.afrosin.repository.room.HistoryDataBase
import com.afrosin.repository.room.RoomDataBaseImplementation
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(
            get(),
            HistoryDataBase::class.java,
            "HistoryDB"
        ).build()
    }
    single { get<HistoryDataBase>().historyDao() }
    single<IRepository<List<DataModel>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<IRepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(
                get()
            )
        )
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}