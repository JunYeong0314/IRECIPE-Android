package com.umcproject.irecipe.data.remote.request.comment


import com.squareup.moshi.Json

data class SetQARequest(
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "parentId")
    val parentId: Int?
)