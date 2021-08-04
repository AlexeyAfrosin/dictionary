package com.afrosin.dictionary.di

import com.afrosin.dictionary.model.api.RetrofitImplementation
import com.afrosin.dictionary.model.data.DataModel
import com.afrosin.dictionary.model.dataSource.IDataSource
import com.afrosin.dictionary.repository.IRepository
import com.afrosin.dictionary.repository.RepositoryImplementation
import com.afrosin.dictionary.room.RoomDataBaseImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: IDataSource<List<DataModel>>): IRepository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: IDataSource<List<DataModel>>): IRepository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): IDataSource<List<DataModel>> = RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): IDataSource<List<DataModel>> =
        RoomDataBaseImplementation()
}