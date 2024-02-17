package com.umcproject.irecipe.data.remote.service.home

import com.umcproject.irecipe.data.remote.response.home.GetPostRankingCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPostRankingCategoryService {
    @GET("/post/ranking/{category}")
    suspend fun getPostRankingCategory(
        @Query("page") page: Int,
        @Query("category") category: String
    ): Response<GetPostRankingCategoryResponse>
}