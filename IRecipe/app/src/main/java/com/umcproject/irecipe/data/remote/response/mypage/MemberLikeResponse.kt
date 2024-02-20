package com.umcproject.irecipe.data.remote.response.mypage

import com.squareup.moshi.Json

data class MemberLikeResponse(
    @Json(name = "code")
    val code: String?,
    @Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "result")
    val result: List<Result>?
)

data class Result(
    @Json(name = "category")
    val category: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "level")
    val level: Any?,
    @Json(name = "likeOrNot")
    val likeOrNot: Boolean?,
    @Json(name = "likes")
    val likes: Int?,
    @Json(name = "myPost")
    val myPost: Boolean?,
    @Json(name = "postId")
    val postId: Int?,
    @Json(name = "reviewsCount")
    val reviewsCount: Int?,
    @Json(name = "score")
    val score: Double?,
    @Json(name = "status")
    val status: Any?,
    @Json(name = "subhead")
    val subhead: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "writerImage")
    val writerImage: String?,
    @Json(name = "writerNickName")
    val writerNickName: String?
)