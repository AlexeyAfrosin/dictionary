package com.afrosin.dictionary.di

import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.api.RetrofitImplementation
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.repository.IRepository
import com.afrosin.dictionary.repository.RepositoryImplementation
import com.afrosin.dictionary.room.RoomDataBaseImplementation
import com.afrosin.dictionary.viewmodels.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<IRepository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single<IRepository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(
            RoomDataBaseImplementation()
        )
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}