package com.umcproject.irecipe.data.remote.response.comment.setReview


import com.squareup.moshi.Json

data class SetReviewResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: Result?
)

data class Result(
    @field:Json(name = "context")
    val context: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "score")
    val score: Double?
)