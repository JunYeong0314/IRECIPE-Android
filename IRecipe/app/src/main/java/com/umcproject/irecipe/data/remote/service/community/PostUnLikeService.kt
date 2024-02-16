package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.community.postLike.PostLikeResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface PostUnLikeService {
    @DELETE("/post/like/{postId}")
    suspend fun postUnLike(
        @Path("postId") postId: Int
    ): Response<PostLikeResponse>
}