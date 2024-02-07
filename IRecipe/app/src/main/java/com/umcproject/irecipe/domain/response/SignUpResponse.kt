package com.umcproject.irecipe.domain.response


import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "code")
    val code: String?,
    @Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "result")
    val result: Result?
)

data class Result(
    @Json(name = "accessToken")
    val accessToken: String?,
    @Json(name = "accessTokenExpiresIn")
    val accessTokenExpiresIn: Int?,
    @Json(name = "grantType")
    val grantType: String?,
    @Json(name = "memberId")
    val memberId: Int?,
    @Json(name = "refreshToken")
    val refreshToken: String?
)