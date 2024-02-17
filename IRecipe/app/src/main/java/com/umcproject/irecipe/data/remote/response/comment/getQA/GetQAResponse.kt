package com.umcproject.irecipe.data.remote.response.comment.getQA


import com.squareup.moshi.Json

data class GetQAResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: List<Result?>?
)
data class Result(
    @field:Json(name = "children")
    val children: List<Children?>?,
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "createdAt")
    val createdAt: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "memberId")
    val memberId: Int?,
    @field:Json(name = "memberImage")
    val memberImage: String?,
    @field:Json(name = "memberNickName")
    val memberNickName: String?,
    @field:Json(name = "qnaId")
    val qnaId: Int?
)
data class Children(
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "createdAt")
    val createdAt: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "memberId")
    val memberId: Int?,
    @field:Json(name = "memberImage")
    val memberImage: String?,
    @field:Json(name = "memberNickName")
    val memberNickName: String?,
    @field:Json(name = "qnaId")
    val qnaId: Int?
)