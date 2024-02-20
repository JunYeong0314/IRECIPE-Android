package com.umcproject.irecipe.data.remote.response.community.postUpdate

import com.squareup.moshi.Json

data class PostUpdateResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "result")
    val result: Result
)

data class Result(
    @field:Json(name = "postId")
    val postId: Int
)