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
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "likeOrNot")
    val likeOrNot: Boolean?,
    @Json(name = "likes")
    val likes: Int?,
    @Json(name = "memberImage")
    val memberImage: String?, //Any
    @Json(name = "nickName")
    val nickName: String?,
    @Json(name = "postId")
    val postId: Int?,
    @Json(name = "reviewsCount")
    val reviewsCount: Int?,
    @Json(name = "score")
    val score: Double?, //Int
    @Json(name = "subhead")
    val subhead: String?,
    @Json(name = "title")
    val title: String?
)