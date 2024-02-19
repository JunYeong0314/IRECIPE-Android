package com.umcproject.irecipe.data.remote.response.refrigerator

import com.squareup.moshi.Json

data class RefrigeratorDeleteResponse(
    @field:Json(name = "code")
    val code: String,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "result")
    val result: RefriDeleteResult
)
data class RefriDeleteResult(
    @field:Json(name = "message")
    val message: String
)