package com.umcproject.irecipe.data.remote.service.home

import com.umcproject.irecipe.data.remote.response.home.GetPostRanking
import retrofit2.Response
import retrofit2.http.GET

interface GetPostRankingService {
    @GET("/post/ranking")
    suspend fun getPostRanking(
//        @Query("page") page: Int
    ): Response<GetPostRanking>
}