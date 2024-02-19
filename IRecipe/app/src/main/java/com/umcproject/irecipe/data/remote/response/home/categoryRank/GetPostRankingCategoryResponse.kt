package com.umcproject.irecipe.data.remote.response.home.categoryRank


import com.squareup.moshi.Json

data class GetPostRankingCategoryResponse(
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
    @field:Json(name = "isFirst")
    val isFirst: Boolean?,
    @field:Json(name = "isLast")
    val isLast: Boolean?,
    @field:Json(name = "listSize")
    val listSize: Int?,
    @field:Json(name = "postList")
    val postList: List<Post?>?,
    @field:Json(name = "totalElements")
    val totalElements: Int?,
    @field:Json(name = "totalPage")
    val totalPage: Int?
)

data class Post(
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "likes")
    val likes: Int?,
    @field:Json(name = "postId")
    val postId: Int?,
    @field:Json(name = "scores")
    val scores: Double,
    @field:Json(name = "scoresInOneMonth")
    val scoresInOneMonth: Double?,
    @field:Json(name = "title")
    val title: String?
)