package com.umcproject.irecipe.domain.model

data class PostDetail(
    val title: String?,
    val subTitle: String?,
    val content: String?,
    val level: String?,
    val category: String?,
    val postImageUrl: String?,
    val writerNick: String?,
    val writerProfileUrl: String?,
    val likes: Int?,
    val score: Double?,
    val reviewCount: Int?,
    val createdAt: String?,
    val isLike: Boolean?
)
