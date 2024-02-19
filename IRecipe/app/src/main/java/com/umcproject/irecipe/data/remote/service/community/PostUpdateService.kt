package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.request.community.PostUpdateRequest
import com.umcproject.irecipe.data.remote.response.community.postUpdate.PostUpdateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface PostUpdateService {
    @Multipart
    @PATCH("/post/{postId}")
    suspend fun postUpdate(
        @Path("postId") postId:Int,
        @Part("postUpdateRequest") postUpdateRequest: PostUpdateRequest,
        @Part image: MultipartBody.Part?
    ): Response<PostUpdateResponse>
}