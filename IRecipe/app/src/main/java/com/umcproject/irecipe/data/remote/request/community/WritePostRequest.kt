package com.umcproject.irecipe.data.remote.request.community


import com.squareup.moshi.Json

data class WritePostRequest(
    @Json(name = "category")
    val category: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "level")
    val level: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "subhead")
    val subhead: String?,
    @Json(name = "title")
    val title: String?
)