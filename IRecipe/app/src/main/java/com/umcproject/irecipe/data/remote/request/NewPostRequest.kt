package com.umcproject.irecipe.data.remote.request


import com.squareup.moshi.Json

data class NewPostRequest(
    @field:Json(name = "file")
    val `file`: String?,
    @field:Json(name = "postRequestDTO")
    val postRequestDTO: PostRequestDTO?
)
data class PostRequestDTO(
    @field:Json(name = "category")
    val category: String?,
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "level")
    val level: String?,
    @field:Json(name = "status")
    val status: String?, // 임시저장이면 TEMP 저장이면 POST
    @field:Json(name = "subhead")
    val subhead: String?,
    @field:Json(name = "title")
    val title: String?
)