package com.umcproject.irecipe.data.remote.response.community.postDelete

import com.squareup.moshi.Json

data class PostDeleteResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: String?
)