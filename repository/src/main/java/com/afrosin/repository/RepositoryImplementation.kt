package com.afrosin.repository

import com.afrosin.model.data.dto.DataModelDto

class RepositoryImplementation(private val dataSource: IDataSource<List<DataModelDto>>) :
    IRepository<List<DataModelDto>> {

    override suspend fun getData(word: String): List<DataModelDto> {
        return dataSource.getData(word)
    }
}

