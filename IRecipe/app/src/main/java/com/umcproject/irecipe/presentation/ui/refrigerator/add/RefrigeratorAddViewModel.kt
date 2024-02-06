package com.umcproject.irecipe.presentation.ui.refrigerator.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umcproject.irecipe.domain.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RefrigeratorAddViewModel: ViewModel() {
    private val _foodInfo = MutableStateFlow<Food>(Food())
    private val foodInfo: StateFlow<Food>
        get() = _foodInfo.asStateFlow()

    private val _isComplete = MutableLiveData<Boolean>(false)
    val isComplete: LiveData<Boolean>
        get() = _isComplete

    fun setName(name: String){
        _foodInfo.update { it.copy(name = name) }
        isComplete()
    }

    fun setExpiration(expiration: String){
        _foodInfo.update { it.copy(expiration = expiration) }
        isComplete()
    }

    fun setType(type: String){
        _foodInfo.update { it.copy(type = type) }
        isComplete()
    }

    fun setSaveInfo(saveInfo: String){
        _foodInfo.update { it.copy(saveInfo = saveInfo) }
        isComplete()
    }

    fun setMemo(memo: String){
        _foodInfo.update { it.copy(memo = memo) }
    }

    private fun isComplete(){
        _isComplete.value = (_foodInfo.value.name != "" && _foodInfo.value.expiration != ""
                && _foodInfo.value.type != "" && _foodInfo.value.saveInfo != "")
        Log.d("TEST", _foodInfo.value.toString())
    }
}