package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.service.mypage.MemberLikeService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.MyPost
import com.umcproject.irecipe.domain.repository.MemberLikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MemberLikeRepositoryImpl(
    private val memberLikeService: MemberLikeService
): MemberLikeRepository {

    override fun fetchLike(page: Int): Flow<State<List<MyPost>>> = flow {
        emit(State.Loading)

        val response = memberLikeService.memberLikeService(page = page)
        val statusCode = response.code()

        if (statusCode == 200) {
            val postList = response.body()?.result?.map { it ->
                MyPost(
                    title = it?.title ?: "",
                    subTitle = it?.subhead ?: "",
                    content = it?.content ?: "",
                    likes = it?.likes,
                    imageUrl = it?.imageUrl ?: "",
                    fileName = it?.fileName ?: "",
                    category = it?.category ?: "",
                    level = it?.level ?: "",
                    score = it?.score,
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