package com.umcproject.irecipe.data.remote.response.comment.setQA


import com.squareup.moshi.Json

data class SetQAResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: Result?
)

data class Result(
    @field:Json(name = "qnaId")
    val qnaId: Int?
)