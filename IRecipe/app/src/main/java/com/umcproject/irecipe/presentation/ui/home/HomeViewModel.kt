package com.umcproject.irecipe.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
):ViewModel() {
    // 유통기한은 나중에 생각
    // 랭킹가져오기
    init {
        fetchRank(0)
    }

    private var postRankList = emptyList<PostRank>()
    private var postRankCategoryList = emptyList<PostRank>()
    private val _fetchState = MutableLiveData<Int?>(null)
    val fetchState: LiveData<Int?>
        get() = _fetchState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private fun fetchRank(page: Int) {
        viewModelScope.launch {
            postRepository.fetchPostRanking(page).collect{ state->
                when(state){
                    is State.Error -> {_errorMessage.value = state.exception.message}
                    State.Loading -> {}
                    is State.ServerError -> { _fetchState.value = state.code }
                    is State.Success -> {
                        postRankList = state.data
                        _fetchState.value = 200

                    }
                }
            }
        }
    }

    fun getPostRank(): List<PostRank>{
        return postRankList
    }

    private fun fetchRankCategotry(page: Int, category: String) {
        viewModelScope.launch {
            postRepository.fetchPostRankingCategory(page,category).collect{ state->
                when(state){
                    is State.Error -> {_errorMessage.value = state.exception.message}
                    State.Loading -> {}
                    is State.ServerError -> { _fetchState.value = state.code }
                    is State.Success -> {
                        postRankList = state.data
                        _fetchState.value = 200

                    }
                }
            }
        }
    }

    fun getPostRankCategory(): List<PostRank>{
        return postRankCategoryList
    }
}