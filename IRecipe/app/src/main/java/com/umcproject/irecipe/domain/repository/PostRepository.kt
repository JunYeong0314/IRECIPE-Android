package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.PostDetail
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun fetchPost(page: Int, criteria: String): Flow<State<List<Post>>>
    fun getPostDetailInfo(postId: Int): Flow<State<PostDetail>>
    fun setLikePost(postId: Int): Flow<State<Int>>
    fun setUnLikePost(postId: Int): Flow<State<Int>>
}