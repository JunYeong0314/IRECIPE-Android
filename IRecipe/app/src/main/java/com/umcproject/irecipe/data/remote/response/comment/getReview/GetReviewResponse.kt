package com.umcproject.irecipe.data.remote.response.comment.getReview


import com.squareup.moshi.Json

data class GetReviewResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: List<Result?>?
)

data class Result(
    @field:Json(name = "context")
    val context: String?,
    @field:Json(name = "createdAt")
    val createdAt: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "memberId")
    val memberId: String?,
    @field:Json(name = "memberImage")
    val memberImage: String?,
    @field:Json(name = "memberNickname")
    val memberNickname: String?,
    @field:Json(name = "reviewId")
    val reviewId: Int?,
    @field:Json(name = "score")
    val score: Int?
)