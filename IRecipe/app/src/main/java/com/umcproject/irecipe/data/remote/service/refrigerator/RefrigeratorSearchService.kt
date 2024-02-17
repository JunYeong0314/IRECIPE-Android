package com.umcproject.irecipe.data.remote.service.refrigerator

import com.umcproject.irecipe.data.remote.response.refrigerator.RefrigeratorSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RefrigeratorSearchService {
    @GET("/ingredients/search")
    suspend fun refrigeratorSearch(
        @Query("name") name: String,
        @Query("page") page: Int
    ):Response<RefrigeratorSearchResponse>
}