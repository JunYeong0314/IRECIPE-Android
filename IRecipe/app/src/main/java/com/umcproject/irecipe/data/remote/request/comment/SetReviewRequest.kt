package com.umcproject.irecipe.data.remote.request.comment


import com.squareup.moshi.Json

data class SetReviewRequest(
    @field:Json(name = "context")
    val context: String?,
    @field:Json(name = "score")
    val score: Int?
)