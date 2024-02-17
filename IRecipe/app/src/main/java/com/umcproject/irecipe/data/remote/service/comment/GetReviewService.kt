package com.umcproject.irecipe.data.remote.service.comment

import com.umcproject.irecipe.data.remote.response.comment.getReview.GetReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetReviewService {
    @GET("/post/{postId}/review")
    suspend fun getReview(
        @Path("postId") postId: Int,
        @Query("page") page: Int,
    ): Response<GetReviewResponse>
}