package com.umcproject.irecipe.presentation.ui.refrigerator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.service.login.NickDuplicationService
import com.umcproject.irecipe.data.remote.service.refrigerator.RefrigeratorSearchService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.domain.repository.RefrigeratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.sql.Ref
import javax.inject.Inject

@HiltViewModel
class RefrigeratorViewModel @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository,
): ViewModel() {
    // 냉장고 재료 불러오기
    init {
        allIngredientFetch()
    }

    private var searchIngredientList = emptyList<Ingredient>()
    private var normalIngredient: Refrigerator? = null
    private var coldIngredient: Refrigerator? = null
    private var frozenIngredient: Refrigerator? = null
    private var normalIngredientList = mutableListOf<Ingredient>()
    private var coldIngredientList = mutableListOf<Ingredient>()
    private var frozenIngredientList = mutableListOf<Ingredient>()

    private val _fetchState = MutableLiveData<Int>()
    val fetchState: LiveData<Int>
        get() = _fetchState

    private val _detailState = MutableLiveData<Int>()
    val detailState: LiveData<Int>
        get() = _detailState

    private val _searchState = MutableLiveData<Int>()
    val searchState: LiveData<Int> get() = _searchState

    private val _deleteState = MutableLiveData<Int>()
    val deleteState: LiveData<Int> get() = _deleteState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage


    private fun fetchRefrigerator(type: String, page: Int){
        viewModelScope.launch {
            refrigeratorRepository.fetchIngredientType(type, page).collectLatest{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        when(type){
                            "AMBIENT" -> normalIngredient = state.data
                            "REFRIGERATED" -> coldIngredient = state.data
                            "FROZEN" -> frozenIngredient = state.data
                        }
                        _fetchState.value = 200

                        if(state.data.ingredient.isNotEmpty()){
                            when(type){
                                "AMBIENT" -> {
                                    if(page == 0) normalIngredientList.clear()
                                    normalIngredientList.addAll(state.data.ingredient)
                                }
                                "REFRIGERATED" -> {
                                    if(page == 0) coldIngredientList.clear()
                                    coldIngredientList.addAll(state.data.ingredient)
                                }
                                "FROZEN" -> {
                                    if(page == 0) frozenIngredientList.clear()
                                    frozenIngredientList.addAll(state.data.ingredient)
                                }
                            }
                            _detailState.value = 200
                        }
                    }
                    is State.ServerError -> {
                        _fetchState.value = state.code
                        _detailState.value = state.code
                    }
                    is State.Error -> { _errorMessage.value = state.exception.message }
                }
            }
        }
    }

    fun searchRefrigerator(food:String){
        viewModelScope.launch {
            refrigeratorRepository.searchIngredient(food).collect{state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        searchIngredientList = state.data
                        _searchState.value = 200
                    }
                    is State.ServerError -> { _searchState.value = state.code }
                    is State.Error -> { _errorMessage.value = state.exception.message }
                }
            }
        }
    }

    fun deleteIngredient(ingredientId: Int?){
        viewModelScope.launch {
            ingredientId?.let {
                refrigeratorRepository.deleteIngredient(ingredientId).collect{ state->
                    when(state){
                        is State.Loading -> {}
                        is State.Success -> {
                            _deleteState.value = 200
                        }
                        is State.ServerError -> {  _deleteState.value = state.code }
                        is State.Error -> { _errorMessage.value = state.exception.message }
                    }
                }
            }
        }
    }

    fun getSearchIngredientList(): List<Ingredient> {
        return searchIngredientList
    }

    fun getNormalIngredient(): Refrigerator?{
        return normalIngredient
    }

    fun getColdIngredient(): Refrigerator?{
        return coldIngredient
    }

    fun getFrozenIngredient(): Refrigerator?{
        return frozenIngredient
    }

    fun getNormalIngredientList(): List<Ingredient>{
        return normalIngredientList
    }

    fun getColdIngredientList(): List<Ingredient>{
        return coldIngredientList
    }

    fun getFrozenIngredientList(): List<Ingredient>{
        return frozenIngredientList
    }

    fun allIngredientFetch(){
        fetchRefrigerator("AMBIENT", 0)
        fetchRefrigerator("REFRIGERATED", 0)
        fetchRefrigerator("FROZEN", 0)
    }

    fun fetchType(type: String, page: Int) = fetchRefrigerator(type, page)
}
