package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.community.getPost.GetPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPostService {
    @GET("/post/paging")
    suspend fun getPost(
        @Query("page") page: Int,
        @Query("criteria") criteria: String
    ): Response<GetPostResponse>
}