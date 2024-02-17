package com.umcproject.irecipe.data.remote.service.comment

import com.umcproject.irecipe.data.remote.response.comment.getQA.GetQAResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetQAService {
    @GET("/post/{postId}/qna")
    suspend fun getQA(
        @Path("postId") postId: Int
    ): Response<GetQAResponse>
}