package com.umcproject.irecipe.data.remote.request


import com.squareup.moshi.Json

data class LoginRequest(
    @field:Json(name = "personalId")
    val personalId: String?
)