package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.community.postDelete.PostDeleteResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface PostDeleteService {
    @DELETE("/post/{postId}")
    suspend fun postDelete(
        @Path("postId") postId: Int
    ):Response<PostDeleteResponse>
}