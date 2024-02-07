package com.umcproject.irecipe.presentation.ui.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.domain.model.User
import com.umcproject.irecipe.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
): ViewModel() {
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

    fun setInit(){
        viewModelScope.launch {
            _userInfo.update {
                it.copy(num = userDataRepository.getUserData().num, token = userDataRepository.getUserData().token)
            }
        }
    }

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

    fun setAllergy(allergy: List<String>){
        _userInfo.update { it->
            it.copy(allergy = allergy)
        }
        Log.d("TEST", _userInfo.value.toString())
    }

    fun setLastComplete(isComplete: Boolean){
        _isLastComplete.value = true
    }

    private fun checkFirstStep(){
        _isFirstComplete.value = _userInfo.value.name != "" && _userInfo.value.gender != "" && _userInfo.value.age != ""
    }

    private fun checkSecondStep(): Boolean{
        return _userInfo.value.nick != ""
    }

}