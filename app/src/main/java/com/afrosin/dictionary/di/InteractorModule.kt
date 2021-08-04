package com.afrosin.dictionary.di

import com.afrosin.dictionary.interactor.MainInteractor
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.repository.IRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {
    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: IRepository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: IRepository<List<DataModel>>,
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}