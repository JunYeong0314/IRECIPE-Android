package com.umcproject.irecipe.data.remote.service.comment

import com.umcproject.irecipe.data.remote.request.comment.SetReviewRequest
import com.umcproject.irecipe.data.remote.response.comment.setReview.SetReviewResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SetReviewService {
    @Multipart
    @POST("/post/{postId}/review")
    suspend fun setReview(
        @Path("postId") postId: Int,
        @Part("ReviewRequestDTO") setReviewRequest: SetReviewRequest,
        @Part image: MultipartBody.Part?
    ): Response<SetReviewResponse>
}