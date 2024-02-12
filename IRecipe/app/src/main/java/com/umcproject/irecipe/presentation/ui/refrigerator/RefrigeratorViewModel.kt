package com.umcproject.irecipe.presentation.ui.refrigerator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.domain.repository.RefrigeratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Ref
import javax.inject.Inject

@HiltViewModel
class RefrigeratorViewModel @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
): ViewModel() {

    // 냉장고 재료 불러오기
    init {
        viewModelScope.launch {
            refrigeratorRepository.fetchRefrigerator(0).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> { _fetchRefrigerator.value = state.data }
                    is State.ServerError -> { _fetchRefrigerator.value = state.code }
                    is State.Error -> { _fetchRefrigerator.value = -1 }
                }
            }
        }
    }

    private val _fetchRefrigerator = MutableLiveData<Int?>(null)
    val fetchRefrigerator: LiveData<Int?>
        get() = _fetchRefrigerator

    fun getNormalIngredient(): Refrigerator{
        Log.d("TEST", refrigeratorRepository.getNormalIngredient().toString())
        return refrigeratorRepository.getNormalIngredient()
    }

    fun getColdIngredient(): Refrigerator{
        Log.d("TEST", refrigeratorRepository.getColdIngredient().toString())
        return refrigeratorRepository.getColdIngredient()
    }

    fun getFrozenIngredient(): Refrigerator{
        Log.d("TEST", refrigeratorRepository.getFrozenIngredient().toString())
        return refrigeratorRepository.getFrozenIngredient()
    }

}