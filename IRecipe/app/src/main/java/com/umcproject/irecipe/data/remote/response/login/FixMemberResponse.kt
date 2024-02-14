package com.umcproject.irecipe.data.remote.response.login

import com.squareup.moshi.Json

data class FixMemberResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: FixMemberResult?
)

data class FixMemberResult(
    @field:Json(name = "memberId")
    val memberId: Int?,
    @field:Json(name = "updatedAt")
    val updatedAt: String?
)