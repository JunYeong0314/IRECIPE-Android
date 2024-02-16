package com.umcproject.irecipe.data.remote.response.community.postLike


import com.squareup.moshi.Json

data class PostLikeResponse(
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
    @field:Json(name = "likes")
    val likes: Int?,
    @field:Json(name = "postId")
    val postId: Int?
)