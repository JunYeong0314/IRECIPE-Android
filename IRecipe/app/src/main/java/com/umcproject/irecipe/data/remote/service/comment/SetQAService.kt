package com.umcproject.irecipe.data.remote.service.comment

import com.umcproject.irecipe.data.remote.request.comment.SetQARequest
import com.umcproject.irecipe.data.remote.response.comment.setQA.SetQAResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SetQAService {
    @POST("/post/{postId}/qna")
    suspend fun setQA(
        @Path("postId") postId: Int,
        @Part("QnaRequestDTO") setQARequest: SetQARequest,
        @Part image: MultipartBody.Part?
    ): Response<SetQAResponse>
}