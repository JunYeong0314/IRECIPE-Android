package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.service.mypage.MemberWriteService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.MyPost
import com.umcproject.irecipe.domain.repository.MemberWriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MemberWriteRepositoryImpl(
    private val memberWriteService: MemberWriteService
): MemberWriteRepository {

    override fun fetchWrite(page: Int): Flow<State<List<MyPost>>> = flow {
        emit(State.Loading)

        val response = memberWriteService.memberWriteService(page = page)
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