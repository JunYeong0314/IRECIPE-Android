package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.community.getPostDetail.getPostDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetPostDetailService {
    @GET("/post/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Int
    ): Response<getPostDetailResponse>
}