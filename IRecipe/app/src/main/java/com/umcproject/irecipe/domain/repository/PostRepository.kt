package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun fetchPost(page: Int, criteria: String): Flow<State<List<Post>>>
}