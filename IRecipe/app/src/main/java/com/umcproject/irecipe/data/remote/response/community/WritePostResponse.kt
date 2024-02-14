package com.umcproject.irecipe.data.remote.response.community


import com.squareup.moshi.Json

data class WritePostResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: Result?
)
class Result