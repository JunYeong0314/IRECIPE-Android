package com.umcproject.irecipe.presentation.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Post
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

    private val _postState = MutableLiveData<Int>()
    val postState: LiveData<Int>
        get() = _postState

    private val _postError = MutableLiveData<String>()
    val postError: LiveData<String>
        get() = _postError

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
}