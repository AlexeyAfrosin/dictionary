package com.afrosin.repository.api

import com.afrosin.model.data.dto.DataModelDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModelDto>>
}
