package com.umcproject.irecipe.data.remote.service.home

import com.umcproject.irecipe.data.remote.response.home.categoryRank.GetPostRankingCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetPostRankingCategoryService {
    @GET("/post/ranking/{category}")
    suspend fun getPostRankingCategory(
        @Path("category") category: String,
        @Query("page") page: Int,
    ): Response<GetPostRankingCategoryResponse>
}