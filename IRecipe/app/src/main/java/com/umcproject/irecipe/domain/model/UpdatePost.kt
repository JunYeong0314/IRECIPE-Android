package com.umcproject.irecipe.domain.model

data class UpdatePost(
    val title: String?,
    val subTitle: String?,
    val category: String?,
    val level: String?,
    val status: String?,
    val content: String?,
    val oldUrl: String?
)
