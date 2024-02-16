package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.community.postLike.PostLikeResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface PostLikeService {
    @POST("/post/like/{postId}")
    suspend fun postLikeService(
        @Path("postId") postId: Int
    ): Response<PostLikeResponse>
}