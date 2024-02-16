package com.umcproject.irecipe.presentation.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.service.community.GetPostDetailService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.PostDetail
import com.umcproject.irecipe.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {
    init {
        fetchPost(0, "기본순")
    }

    private var postList = emptyList<Post>()
    private var postDetailInfo: PostDetail? = null

    private val _postState = MutableLiveData<Int>()
    val postState: LiveData<Int>
        get() = _postState

    private val _postError = MutableLiveData<String>()
    val postError: LiveData<String>
        get() = _postError

    private val _postDetailState = MutableLiveData<Int>()
    val postDetailState: LiveData<Int>
        get() = _postDetailState

    private val _postDetailError = MutableLiveData<String>()
    val postDetailError: LiveData<String>
        get() = _postDetailError

    fun fetchPost(page: Int, criteria: String){
        viewModelScope.launch {
            postRepository.fetchPost(page, criteria).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        postList = state.data
                        _postState.value = 200
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
}