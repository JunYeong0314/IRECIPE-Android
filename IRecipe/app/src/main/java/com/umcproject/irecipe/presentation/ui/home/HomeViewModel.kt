package com.umcproject.irecipe.presentation.ui.home

import android.util.Log
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
    private var postRankList = mutableListOf<PostRank>()
    private var currentCategory = "ALL"

    private val _rankState = MutableLiveData<Int>()
    val rankState: LiveData<Int>
        get() = _rankState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _firstRankList = MutableLiveData<List<PostRank>?>()
    val firstRankList: LiveData<List<PostRank>?>
        get() = _firstRankList

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean>
        get() = _isEmptyList

    fun fetchRank(page: Int) {
        viewModelScope.launch {
            postRepository.fetchPostRanking(page).collect{ state->
                when(state){
                    is State.Error -> {_errorMessage.value = state.exception.message}
                    is State.Loading -> {}
                    is State.ServerError -> { _rankState.value = state.code }
                    is State.Success -> {
                        if(state.data.isNotEmpty()) {
                            postRankList.addAll(state.data)
                            _rankState.value = 200
                        }
                        if(_firstRankList.value.isNullOrEmpty()){
                            _firstRankList.value = state.data
                        }
                    }
                }
            }
        }
    }

    fun getPostRank(): List<PostRank>{
        return postRankList
    }

    fun fetchRankCategory(page: Int, category: String) {
        if(category == "ALL"){
            fetchRank(page)
        }else{
            viewModelScope.launch {
                postRepository.fetchPostRankingCategory(page,category).collect{ state->
                    when(state){
                        is State.Error -> { _errorMessage.value = state.exception.message }
                        is State.Loading -> {}
                        is State.ServerError -> { _rankState.value = state.code }
                        is State.Success -> {
                            if(currentCategory != category) postRankList.clear()

                            if(state.data.isNotEmpty()){
                                currentCategory = category
                                postRankList.addAll(state.data)
                                _rankState.value = 200
                            }

                            if(state.data.isEmpty() && postRankList.isEmpty()) _isEmptyList.value = true
                        }
                    }
                }
            }
        }
    }

}