package com.umcproject.irecipe.presentation.ui.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umcproject.irecipe.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel: ViewModel() {
    private val _userInfo = MutableStateFlow(User())
    val userInfo: StateFlow<User>
        get() = _userInfo.asStateFlow()

    private val _isFirstComplete = MutableLiveData(false)
    val isFirstComplete: LiveData<Boolean>
        get() = _isFirstComplete

    private val _isSecondComplete = MutableLiveData(false)
    val isSecondComplete: LiveData<Boolean>
        get() = _isSecondComplete

    private val _isLastComplete = MutableLiveData(false)
    val isLastComplete: LiveData<Boolean>
        get() = _isLastComplete

    fun setName(name: String){
        _userInfo.update { it ->
            it.copy(name = name)
        }
        checkFirstStep()
    }

    fun setGender(gender: String){
        _userInfo.update { it->
            it.copy(gender = gender)
        }
        checkFirstStep()
    }

    fun setAge(age: String){
        _userInfo.update { it->
            it.copy(age = age)
        }
        checkFirstStep()
    }

    fun setProfile(uri: Uri?){
        _userInfo.update { it->
            it.copy(photoUri = uri)
        }
        if(checkSecondStep()) _isSecondComplete.value = true
    }

    fun setNick(nick: String){
        _userInfo.update { it->
            it.copy(nick = nick)
        }
        if(checkSecondStep()) _isSecondComplete.value = true
    }

    fun setAllergy(allergy: String){
        _userInfo.update { it->
            it.copy(allergy = allergy)
        }
    }

    fun setLastComplete(isComplete: Boolean){
        _isLastComplete.value = true
    }

    private fun checkFirstStep(){
        _isFirstComplete.value = _userInfo.value.name != "" && _userInfo.value.gender != "" && _userInfo.value.age != ""
    }

    private fun checkSecondStep(): Boolean{
        return _userInfo.value.photoUri != null && _userInfo.value.nick != ""
    }

}