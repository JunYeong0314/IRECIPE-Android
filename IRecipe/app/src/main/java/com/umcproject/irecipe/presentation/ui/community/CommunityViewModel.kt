package com.umcproject.irecipe.presentation.ui.community

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.request.comment.SetReviewRequest
import com.umcproject.irecipe.data.remote.service.comment.SetReviewService
import com.umcproject.irecipe.data.remote.service.community.PostDeleteService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.PostDetail
import com.umcproject.irecipe.domain.model.Review
import com.umcproject.irecipe.domain.model.SetReview
import com.umcproject.irecipe.domain.repository.CommentRepository
import com.umcproject.irecipe.domain.repository.PostRepository
import com.umcproject.irecipe.presentation.util.UriUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val setReviewService: SetReviewService,
    private val commentRepository: CommentRepository
): ViewModel() {
    private var postList = mutableListOf<Post>() // 게시글 전체 List
    private var postDetailInfo: PostDetail? = null // 게시글 단일 정보
    private var reviewList = mutableListOf<Review>() // 후기 전체 List
    private var postSearchList = emptyList<Post>() // 게시글 전체 List
    private var currentSortType = "기본순"

    // 게시글 리스트 상태 LiveData
    private val _postState = MutableLiveData<Int>()
    val postState: LiveData<Int>
        get() = _postState

    private val _postError = MutableLiveData<String>()
    val postError: LiveData<String>
        get() = _postError

    // 단일 게시글 상태 LiveData
    private val _postDetailState = MutableLiveData<Int>()
    val postDetailState: LiveData<Int>
        get() = _postDetailState

    private val _postDetailError = MutableLiveData<String>()
    val postDetailError: LiveData<String>
        get() = _postDetailError

    // 후기 리스트 상태 LiveData
    private val _reviewState = MutableLiveData<Int>()
    val reviewState: LiveData<Int>
        get() = _reviewState

    private val _reviewError = MutableLiveData<String>()
    val reviewError: LiveData<String>
        get() = _reviewError

    private val setReview = SetReview()

    private val _isReviewComplete = MutableLiveData<Boolean>()
    val isReviewComplete: LiveData<Boolean>
        get() = _isReviewComplete

    // 게시글 검색 리스트 상태 LiveData
    private val _postSearchState = MutableLiveData<Int>()
    val postSearchState: LiveData<Int> get() = _postSearchState

    private val _postSearchError = MutableLiveData<String>()
    val postSearchError: LiveData<String> get() = _postSearchError

    // 게시글 삭제 상태 LiveData
    private val _postDeleteState = MutableLiveData<Int>()
    val postDeleteState: LiveData<Int> get() = _postDeleteState

    private val _postDeleteError = MutableLiveData<String>()
    val postDeleteError: LiveData<String> get() = _postDeleteError


    // 게시글 전체조회
    fun fetchPost(page: Int, sort: String){
        viewModelScope.launch {
            postRepository.fetchPost(page, sort).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        if(currentSortType != sort) postList.clear()

                        if(state.data.isNotEmpty()){
                            currentSortType = sort
                            postList.addAll(state.data)
                            _postState.value = 200
                        }
                    }
                    is State.ServerError -> {
                        _postState.value = state.code
                    }
                    is State.Error -> {
                        _postError.value = state.exception.message
                    }
                }
            }
        }
    }

    fun getPostList(): List<Post>{
        return postList
    }

    // 게시글 단일 조회
    fun getPostInfoFetch(postId: Int){
        viewModelScope.launch {
            postRepository.getPostDetailInfo(postId).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        postDetailInfo = state.data
                        _postDetailState.value = 200
                    }
                    is State.ServerError -> { _postDetailState.value = state.code }
                    is State.Error -> { _postDetailError.value = state.exception.message }
                }
            }
        }
    }

    fun getPostInfo(): PostDetail?{
        return postDetailInfo
    }

    fun fetchReview(postId: Int, page: Int){
        viewModelScope.launch {
            commentRepository.fetchReview(postId, page).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        if(state.data.isNotEmpty()){
                            reviewList.addAll(state.data)
                            _reviewState.value = 200
                        }
                    }
                    is State.ServerError -> { _reviewState.value = state.code }
                    is State.Error -> {
                        _reviewError.value = state.exception.message
                        Log.d("ERROR", state.exception.message.toString())
                    }
                }
            }
        }
    }

    fun getReview(): List<Review> {
        return reviewList
    }

    fun setScore(score: Int){
        setReview.score = score
        _isReviewComplete.value = isReviewComplete()
    }

    fun setReviewImage(uri: Uri?){
        setReview.imageUri = uri
    }

    fun setReviewContent(content: String){
        setReview.content = content
        _isReviewComplete.value = isReviewComplete()
    }

    fun setReview(context: Context, postId: Int): Flow<State<Int>> = flow{
        var imagePart: MultipartBody.Part? = null

        val request = SetReviewRequest(
            context = setReview.content,
            score = setReview.score
        )

        setReview.imageUri?.let { uri->
            val file = UriUtil.toFile(context, uri)
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData(name = "file", file.name, image)
        }
        request.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val response = setReviewService.setReview(
            postId = postId,
            setReviewRequest = request,
            image = imagePart
        )
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    private fun isReviewComplete(): Boolean{
        return setReview.content != "" && setReview.score != 0
    }

    // 게시글 검색 조회
    fun fetchSearchPost(page: Int, keyword:String, type:String){
        viewModelScope.launch {
            postRepository.fetchPostSearch(page, keyword, type).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        postSearchList = state.data
                        _postSearchState.value = 200
                    }
                    is State.ServerError -> {
                        _postSearchState.value = state.code
                    }
                    is State.Error -> {
                        _postSearchError.value = state.exception.message
                    }
                }
            }
        }
    }

    fun getPostSearchList(): List<Post>{
        return postSearchList
    }

    fun deletePost(postId: Int){
        viewModelScope.launch {
            postRepository.deletePost(postId).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> { _postDeleteState.value = 200 }
                    is State.ServerError -> { _postDeleteState.value = state.code }
                    is State.Error -> { _postDeleteError.value = state.exception.message }
                }
            }
        }
    }


}