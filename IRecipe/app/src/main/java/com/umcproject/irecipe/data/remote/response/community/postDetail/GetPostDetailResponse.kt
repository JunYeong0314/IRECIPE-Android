package com.umcproject.irecipe.data.remote.response.community.postDetail


import com.squareup.moshi.Json

data class GetPostDetailResponse(
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
    @field:Json(name = "category")
    val category: String?,
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "createdAt")
    val createdAt: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "level")
    val level: String?,
    @field:Json(name = "likeOrNot")
    val likeOrNot: Boolean?,
    @field:Json(name = "likes")
    val likes: Int?,
    @field:Json(name = "postId")
    val postId: Int?,
    @field:Json(name = "reviewsCount")
    val reviewsCount: Int?,
    @field:Json(name = "score")
    val score: Double?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "subhead")
    val subhead: String?,
    @field:Json(name = "myPost")
    val myPost: Boolean?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "writerImage")
    val writerImage: String?,
    @field:Json(name = "writerNickName")
    val writerNickName: String?
)