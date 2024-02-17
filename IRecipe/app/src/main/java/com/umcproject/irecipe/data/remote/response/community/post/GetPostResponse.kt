package com.umcproject.irecipe.data.remote.response.community.post


import com.squareup.moshi.Json

data class GetPostResponse(
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
    @field:Json(name = "createdAt")
    val createdAt: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "likeOrNot")
    val likeOrNot: Boolean?,
    @field:Json(name = "likes")
    val likes: Int?,
    @field:Json(name = "memberImage")
    val memberImage: String?,
    @field:Json(name = "nickName")
    val nickName: String?,
    @field:Json(name = "postId")
    val postId: Int?,
    @field:Json(name = "reviewsCount")
    val reviewsCount: Int?,
    @field:Json(name = "score")
    val score: Double?,
    @field:Json(name = "subhead")
    val subhead: String?,
    @field:Json(name = "title")
    val title: String?
)