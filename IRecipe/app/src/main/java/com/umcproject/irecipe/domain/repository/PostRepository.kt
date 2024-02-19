package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.domain.model.PostDetail
import com.umcproject.irecipe.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun fetchPost(page: Int, criteria: String): Flow<State<List<Post>>>
    fun fetchPostRanking(page: Int): Flow<State<List<PostRank>>>
    fun getPostDetailInfo(postId: Int): Flow<State<PostDetail>>
    fun setLikePost(postId: Int): Flow<State<Int>>
    fun setUnLikePost(postId: Int): Flow<State<Int>>
    fun fetchPostSearch(page: Int, keyword:String, type:String): Flow<State<List<Post>>>
    fun fetchPostRankingCategory(page: Int, category: String): Flow<State<List<PostRank>>>
    fun deletePost(postId: Int): Flow<State<Int>>
}