package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.service.mypage.MemberLikeService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.MyPost
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.repository.MemberLikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MemberLikeRepositoryImpl(
    private val memberLikeService: MemberLikeService
): MemberLikeRepository {

    override fun fetchLike(page: Int): Flow<State<List<Post>>> = flow {
        emit(State.Loading)

        val response = memberLikeService.memberLikeService(page = page)
        val statusCode = response.code()

        if (statusCode == 200) {
            val postList = response.body()?.result?.map { it ->
                Post(
                    postId = it?.postId,
                    title = it?.title ?: "",
                    subTitle = it?.subhead ?: "",
                    postImageUrl = it?.imageUrl ?: "",
                    writerNick = it?.writerNickName ?:"",
                    writerProfileUrl = it?.writerImage ?: "",
                    likes = it?.likes,
                    score = it?.score ,
                    reviewCount = it?.reviewsCount,
                    createdAt = it?.createdAt ?: "",
                    isLike = it?.likeOrNot,
                )
            } ?: emptyList()

            emit(State.Success(postList))
        } else {
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }
}