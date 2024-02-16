package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.MyPost
import kotlinx.coroutines.flow.Flow

interface MemberLikeRepository {
    fun fetchLike(page: Int): Flow<State<List<MyPost>>>
}