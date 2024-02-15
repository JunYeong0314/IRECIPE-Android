package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.service.community.GetPostService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val getPostService: GetPostService
): PostRepository {
    override fun fetchPost(page: Int, criteria: String): Flow<State<List<Post>>> = flow {
        emit(State.Loading)

        val response = getPostService.getPost(page = page, criteria = mapperToCriteria(criteria))
        val statusCode = response.code()

        if (statusCode == 200) {
            val postList = response.body()?.result?.map { it ->
                Post(
                    postId = it?.postId ?: -1,
                    title = it?.title ?: "",
                    subTitle = it?.subhead ?: "",
                    postImageUrl = it?.imageUrl,
                    writerNick = it?.nickName ?: "",
                    writerProfileUrl = it?.memberImage,
                    likes = it?.likes,
                    score = it?.score,
                    reviewCount = it?.reviewsCount,
                    createdAt = it?.createdAt,
                    isLike = it?.likeOrNot
                )
            } ?: emptyList()

            emit(State.Success(postList))
        } else {
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    private fun mapperToCriteria(sort: String): String{
        return when(sort){
            "기본순" -> "createdAt"
            "인기순" -> "likes"
            "평점 높은 순" -> "score"
            else -> ""
        }
    }
}