package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.service.comment.GetQAService
import com.umcproject.irecipe.data.remote.service.comment.GetReviewService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.QA
import com.umcproject.irecipe.domain.model.Review
import com.umcproject.irecipe.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CommentRepositoryImpl(
    private val getReviewService: GetReviewService,
    private val getQAService: GetQAService
): CommentRepository {
    override fun fetchReview(postId: Int, page: Int): Flow<State<List<Review>>> = flow{
        emit(State.Loading)

        val response = getReviewService.getReview(postId, page)
        val statusCode = response.code()

        if(statusCode == 200){
            val reviewList = response.body()?.result?.map {
                Review(
                    reviewId = it?.reviewId,
                    writerNick = it?.memberNickname,
                    writerProfile = it?.memberImage,
                    content = it?.context,
                    score = it?.score,
                    imageUrl = it?.imageUrl,
                    createdAt = it?.createdAt
                )
            } ?: emptyList()

            emit(State.Success(reviewList))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun fetchQA(postId: Int): Flow<State<List<QA>>> = flow{
        emit(State.Loading)

        val response = getQAService.getQA(postId)
        val statusCode = response.code()

        if(statusCode == 200){
            val qaList = response.body()?.result?.map {

            }
        }
    }
}