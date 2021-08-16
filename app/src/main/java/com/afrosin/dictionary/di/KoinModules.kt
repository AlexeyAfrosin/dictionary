package com.afrosin.dictionary.di

import androidx.room.Room
import com.afrosin.dictionary.interactor.HistoryInteractor
import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.api.RetrofitImplementation
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.repository.IRepository
import com.afrosin.dictionary.repository.IRepositoryLocal
import com.afrosin.dictionary.repository.RepositoryImplementation
import com.afrosin.dictionary.repository.RepositoryImplementationLocal
import com.afrosin.dictionary.room.HistoryDataBase
import com.afrosin.dictionary.room.RoomDataBaseImplementation
import com.afrosin.dictionary.viewmodels.HistoryViewModel
import com.afrosin.dictionary.viewmodels.MainViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "history_db").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<IRepository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<IRepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
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