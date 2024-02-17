package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.QA
import com.umcproject.irecipe.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun fetchReview(postId: Int, page: Int): Flow<State<List<Review>>>
    fun fetchQA(postId: Int): Flow<State<List<QA>>>
}