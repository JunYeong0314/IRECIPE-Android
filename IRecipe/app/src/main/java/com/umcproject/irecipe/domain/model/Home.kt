package com.umcproject.irecipe.domain.model

data class Home(
    val type: HomeType,
    val rank: List<PostRank>?,
    val expiration: List<Ingredient>?
)
data class PostRank(
    val postId: Int?,
    val title: String?,
    val imageUrl: String?,
    val likes: Int?,
    val score: Double?,
    val scoresInOneMonth: Double?
)

enum class HomeType{
    RANK, EXPIRATION
}