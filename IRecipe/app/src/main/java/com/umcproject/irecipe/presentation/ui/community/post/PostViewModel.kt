package com.umcproject.irecipe.presentation.ui.community.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.service.community.PostLikeService
import com.umcproject.irecipe.data.remote.service.community.PostUnLikeService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val postLikeService: PostLikeService,
    private val postUnLikeService: PostUnLikeService
): ViewModel(){

    private val _likeState = MutableLiveData<Int?>(null)
    val likeState: LiveData<Int?>
        get() = _likeState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _unlikeState = MutableLiveData<Int?>(null)
    val unlikeState: LiveData<Int?>
        get() = _unlikeState

    private val _unerrorMessage = MutableLiveData<String?>()
    val unerrorMessage: LiveData<String?>
        get() = _unerrorMessage

    fun postLike(postId: Int) {
        viewModelScope.launch {
            postRepository.setLikePost(postId).collect{ state ->
                when(state){
                    is State.Loading -> {}
                    is State.Error -> {_errorMessage.value = state.exception.message}
                    is State.ServerError -> {_likeState.value=state.code}
                    is State.Success -> {_likeState.value = 200 }
                    else -> {}
                }
            }
        }
    }
    fun postUnLike(postId: Int) {
        viewModelScope.launch {
            postRepository.setUnLikePost(postId).collect{state ->
                when(state){
                    is State.Loading -> {}
                    is State.Error -> {_unerrorMessage.value = state.exception.message}
                    is State.ServerError -> {_unlikeState.value=state.code}
                    is State.Success -> {_unlikeState.value = 200 }
                    else -> {}
                }

            }
        }
    }
}