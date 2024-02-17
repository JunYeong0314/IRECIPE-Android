package com.umcproject.irecipe.domain.model

data class Post(
    val postId: Int?,
    val title: String?,
    val subTitle: String?,
    val postImageUrl: String?,
    val writerNick: String?,
    val writerProfileUrl: String?,
    val likes: Int?,
    val score: Double?,
    val reviewCount: Int?,
    val createdAt: String?,
    val isLike: Boolean?
)
