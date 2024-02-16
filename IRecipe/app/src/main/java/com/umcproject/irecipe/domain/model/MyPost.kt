package com.umcproject.irecipe.domain.model

data class MyPost(
    val title: String?,
    val subTitle: String?,
    val content: String?,
    val likes: Int?,
    val imageUrl: String?,
    val fileName: String?,
    val category: String?,
    val level: String?,
    val score: Int?,
)
