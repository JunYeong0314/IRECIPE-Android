package com.umcproject.irecipe.domain.model

data class PostRank(
    val postId: Int,
    val title: String?,
    val imageUrl: String?,
    val likes: Int?,
    val score: Double?,
    val scoresInOneMonth: Double?
)
