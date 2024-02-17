package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.MyPost
import com.umcproject.irecipe.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface MemberWriteRepository {
    fun fetchWrite(page: Int): Flow<State<List<Post>>>
}