package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.service.community.GetPostDetailService
import com.umcproject.irecipe.data.remote.service.community.GetPostService
import com.umcproject.irecipe.data.remote.service.home.GetPostRankingService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.data.remote.service.community.PostLikeService
import com.umcproject.irecipe.data.remote.service.community.PostSearchService
import com.umcproject.irecipe.data.remote.service.community.PostUnLikeService
import com.umcproject.irecipe.domain.model.PostDetail
import com.umcproject.irecipe.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val getPostService: GetPostService,
    private val getPostRankingService: GetPostRankingService,
    private val getPostDetailService: GetPostDetailService,
    private val postLikeService: PostLikeService,
    private val postUnLikeService: PostUnLikeService,
    private val getpostSearchService: PostSearchService
): PostRepository {
    override fun fetchPost(page: Int, criteria: String): Flow<State<List<Post>>> = flow {
        emit(State.Loading)

        val response = getPostService.getPost(page = page, criteria = mapperToCriteria(criteria))
        val statusCode = response.code()

        if (statusCode == 200) {
            val postList = response.body()?.result?.map { it ->
                Post(
                    postId = it?.postId ?: -1,
                    title = it?.title ?: "",
                    subTitle = it?.subhead ?: "",
                    postImageUrl = it?.imageUrl,
                    writerNick = it?.nickName ?: "",
                    writerProfileUrl = it?.memberImage,
                    likes = it?.likes,
                    score = it?.score,
                    reviewCount = it?.reviewsCount,
                    createdAt = it?.createdAt,
                    isLike = it?.likeOrNot
                )
            } ?: emptyList()

            emit(State.Success(postList))
        } else {
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun getPostDetailInfo(postId: Int): Flow<State<PostDetail>> = flow{
        emit(State.Loading)

        val response = getPostDetailService.getPostDetail(postId = postId)
        val statusCode = response.code()

        if(statusCode == 200){
            val post = response.body()?.result
            val postInfo = PostDetail(
                title = post?.title,
                subTitle = post?.subhead,
                postImageUrl = post?.imageUrl,
                writerNick = post?.writerNickName,
                writerProfileUrl = post?.writerImage,
                likes = post?.likes,
                score = post?.score,
                reviewCount = post?.reviewsCount,
                createdAt = post?.createdAt,
                isLike = post?.likeOrNot,
                content = post?.content,
                level = post?.level,
                category = post?.category
            )

            emit(State.Success(postInfo))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun setLikePost(postId: Int): Flow<State<Int>> = flow{
        emit(State.Loading)

        val response = postLikeService.postLikeService(postId)
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun setUnLikePost(postId: Int): Flow<State<Int>> = flow{
        emit(State.Loading)

        val response = postUnLikeService.postUnLike(postId)
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    private fun mapperToCriteria(sort: String): String{
        return when(sort){
            "기본순" -> "createdAt"
            "인기순" -> "likes"
            "평점 높은 순" -> "score"
            else -> ""
        }
    }

    override fun fetchPostRanking(): Flow<State<List<PostRank>>> = flow {
        emit(State.Loading)

        val response = getPostRankingService.getPostRanking()
        val statusCode = response.code()

        if (statusCode == 200) {
            val responseBody = response.body()?.result?.postList?.mapNotNull{ post ->
                post?.let {
                    PostRank(
                        it?.postId ?: -1,
                        title = it?.title ?: "",
                        imageUrl = it?.imageUrl,
                        likes = it?.likes,
                        score = it?.scores,
                        scoresInOneMonth = it?.scoresInOneMonth
                    )
                }
            } ?: emptyList()
            emit(State.Success(responseBody))
        } else {
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun fetchPostSearch(page: Int, keyword:String, type:String): Flow<State<List<Post>>> = flow {
        emit(State.Loading)

        val response = getpostSearchService.postSearch(page = page, keyword = keyword, type = mapperToType(type))
        val statusCode = response.code()

        if (statusCode == 200) {
            val postList = response.body()?.result?.map { it ->
                Post(
                    postId = it?.postId ?: -1,
                    title = it?.title ?: "",
                    subTitle = it?.subhead ?: "",
                    postImageUrl = it?.imageUrl,
                    writerNick = it?.nickName ?: "",
                    writerProfileUrl = it?.memberImage,
                    likes = it?.likes,
                    score = it?.score,
                    reviewCount = it?.reviewsCount,
                    createdAt = it?.createdAt,
                    isLike = it?.likeOrNot
                )
            } ?: emptyList()

            emit(State.Success(postList))
        } else {
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    private fun mapperToType(type: String): String{
        return when(type){
            "제목" -> "title"
            "내용" -> "content"
            "작성자" -> "writer"
            else -> ""
        }
    }
}