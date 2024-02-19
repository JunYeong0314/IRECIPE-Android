package com.umcproject.irecipe.data.remote.service.home

import com.umcproject.irecipe.data.remote.response.home.GetPostRankingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPostRankingService {
    @GET("/post/ranking")
    suspend fun getPostRanking(
        @Query("page") page: Int
    ): Response<GetPostRankingResponse>
}