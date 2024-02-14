package com.umcproject.irecipe.data.remote.response.login

import com.squareup.moshi.Json

data class FixMemberResponse(
    @Json(name = "code")
    val code: String,
    @Json(name = "isSuccess")
    val isSuccess: Boolean,
    @Json(name = "message")
    val message: String,
    @Json(name = "result")
    val result:  FixMemberResult
)
data class FixMemberResult(
    @Json(name = "memberID")
    val memberId: Int,
    @Json(name = "updateAt")
    val updatedAt: String
)