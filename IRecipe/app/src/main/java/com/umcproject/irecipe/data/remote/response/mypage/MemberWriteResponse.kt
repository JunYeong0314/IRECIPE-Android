package com.umcproject.irecipe.data.remote.response.mypage

import com.squareup.moshi.Json

data class MemberWriteResponse(
    @Json(name = "code")
    val code: String?,
    @Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "result")
    val result: List<MemberWriteResult>?
)

data class MemberWriteResult(
    @Json(name = "category")
    val category: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "fileName")
    val fileName: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "level")
    val level: String?,
    @Json(name = "likes")
    val likes: Int?,
    @Json(name = "score")
    val score: Int?,
    @Json(name = "subhead")
    val subhead: String?,
    @Json(name = "title")
    val title: String?
)