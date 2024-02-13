package com.umcproject.irecipe.data.remote.response


import com.squareup.moshi.Json

data class NewPostResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "newPostResult")
    val newPostResult: NewPostResult?
)
class NewPostResult