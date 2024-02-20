package com.umcproject.irecipe.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.domain.repository.PostRepository
import com.umcproject.irecipe.domain.repository.RefrigeratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val refrigeratorRepository: RefrigeratorRepository
):ViewModel() {
    private var postRankList = mutableListOf<PostRank>()
    private var currentCategory = "ALL"
    private var firstRankList = listOf<PostRank>()
    private var firstExpirationIngredientList = listOf<Ingredient>()

    private val _rankState = MutableLiveData<Int>()
    val rankState: LiveData<Int>
        get() = _rankState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean>
        get() = _isEmptyList

    private val _expirationIngredientState = MutableLiveData<Int>()
    val expirationIngredientState: LiveData<Int>
        get() = _expirationIngredientState

    private val _isInitData = MutableLiveData<Boolean>()
    val isInitData: LiveData<Boolean>
        get() = _isInitData

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
                        if(firstRankList.isEmpty()){
                            firstRankList = state.data
                            _isInitData.value = checkInitData()
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
            if(currentCategory != category) postRankList.clear()
            currentCategory = category
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

    fun fetchExpirationIngredient(page: Int){
        viewModelScope.launch {
            refrigeratorRepository.fetchExpirationIngredient(page).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        if(firstExpirationIngredientList.isEmpty()){
                            firstExpirationIngredientList = state.data
                            _isInitData.value = checkInitData()
                        }
                    }
                    is State.ServerError -> { _expirationIngredientState.value = state.code }
                    is State.Error -> { _expirationIngredientState.value = -1 }
                }
            }
        }
    }

    fun getFirstRankPost(): List<PostRank> {
        return firstRankList
    }

    fun getFirstExpirationIngredient(): List<Ingredient> {
        return firstExpirationIngredientList
    }

    private fun checkInitData(): Boolean{
        return firstExpirationIngredientList.isNotEmpty() || firstRankList.isNotEmpty()
    }

}