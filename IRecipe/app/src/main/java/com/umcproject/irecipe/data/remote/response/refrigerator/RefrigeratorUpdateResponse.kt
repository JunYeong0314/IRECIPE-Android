package com.umcproject.irecipe.data.remote.response.refrigerator

import com.squareup.moshi.Json

data class RefrigeratorUpdateResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: RefriUpdateResult?
)

data class RefriUpdateResult(
    @field:Json(name = "ingredientId")
    val ingredientId: Int?,
    @field:Json(name = "updatedAt")
    val updatedAt: String?
)