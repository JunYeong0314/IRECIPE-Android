package com.umcproject.irecipe.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.service.login.CheckMemberService
import com.umcproject.irecipe.data.remote.service.login.NickDuplicationService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.User
import com.umcproject.irecipe.presentation.ui.chat.ChatBotActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private var checkMemberService: CheckMemberService,
    private val duplicationService: NickDuplicationService,
): ViewModel() {
    private val _userInfo = MutableStateFlow(User())
    val userInfo: StateFlow<User> get() = _userInfo.asStateFlow()

    private val _nameResponse = MutableLiveData<String>()
    val nameResponse: LiveData<String> get() = _nameResponse

    private val _nicknameResponse = MutableLiveData<String>()
    val nicknameResponse: LiveData<String> get() = _nicknameResponse

    private val _imgResponse = MutableLiveData<String>()
    val imgResponse: LiveData<String> get() = _imgResponse

    private val _genderResponse = MutableLiveData<String>()
    val genderResponse: LiveData<String> get() = _genderResponse

    private val _ageResponse = MutableLiveData<String>()
    val ageResponse: LiveData<String> get() = _ageResponse

    private val _allergyResponse = MutableLiveData<List<String>>()
    val allergyResponse: LiveData<List<String>> get() = _allergyResponse

    private val _isComplete = MutableLiveData(false)
    val isComplete: LiveData<Boolean> get() = _isComplete

    private val _isSecondComplete = MutableLiveData(false)
    val isSecondComplete: LiveData<Boolean> get() = _isSecondComplete

    fun resultName(){
        viewModelScope.launch{
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.name.toString())
            _nameResponse.value = response.body()?.result?.name.toString()
        }
        checkStep()
    }

    fun resultNick(){
        viewModelScope.launch{
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.nickname.toString())
            _nicknameResponse.value = response.body()?.result?.nickname.toString()
        }
        checkStep()
    }

    fun setNick(nick: String): Flow<State<Int>> = flow {
        emit(State.Loading)
        val response = duplicationService.getNickDuplication(nickname = nick)

        if(response.isSuccessful){
            //_userInfo.update { it.copy(nick = nick) }
            emit(State.Success(200))
        }else{
            emit(State.Success(400))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    fun resultImg(){
        viewModelScope.launch {
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.imageUrl.toString())
            _imgResponse.value = response.body()?.result?.imageUrl.toString()
        }
    }

    fun resultGender(){
        viewModelScope.launch {
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.gender.toString())
            _genderResponse.value = response.body()?.result?.gender.toString()
        }
        checkStep()
    }

    fun resultAge(){
        viewModelScope.launch {
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.age.toString())
            _ageResponse.value = response.body()?.result?.age.toString()
        }
        checkStep()
    }

    fun resultAllergy(){
        viewModelScope.launch {
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.allergyList.toString())
            _allergyResponse.value = (response.body()?.result?.allergyList ?: emptyList()) as List<String>?
        }
    }
    fun setNickComplete(check: Boolean){
        _isSecondComplete.value = check
    }

    private fun checkStep(){
        _isComplete.value = _nameResponse.value != "" && _genderResponse.value != "" && _nicknameResponse.value != ""
    }
}