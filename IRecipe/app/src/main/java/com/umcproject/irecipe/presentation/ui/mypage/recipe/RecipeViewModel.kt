package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.service.login.CheckMemberService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.MyPost
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.repository.MemberLikeRepository
import com.umcproject.irecipe.domain.repository.MemberWriteRepository
import com.umcproject.irecipe.presentation.ui.chat.ChatBotActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val memberWriteRepository: MemberWriteRepository,
    private val memberLikeRepository: MemberLikeRepository,
    private var checkMemberService: CheckMemberService,
): ViewModel() {

    init {
        fetchWrite(0)
    }

    private var myWriteList = emptyList<MyPost>()

    private val _myWriteState = MutableLiveData<Int>()
    val myWriteState: LiveData<Int> get() = _myWriteState

    private val _myWriteError = MutableLiveData<String>()
    val myWriteError: LiveData<String> get() = _myWriteError

    private var myLikeList = emptyList<MyPost>()

    private val _myLikeState = MutableLiveData<Int>()
    val myLikeState: LiveData<Int> get() = _myLikeState

    private val _myLikeError = MutableLiveData<String>()
    val myLikeError: LiveData<String> get() = _myLikeError

    private val _nicknameResponse = MutableLiveData<String>()
    val nicknameResponse: LiveData<String> get() = _nicknameResponse

    private val _imgResponse = MutableLiveData<String>()
    val imgResponse: LiveData<String> get() = _imgResponse

    fun fetchWrite(page: Int){
        viewModelScope.launch {
            memberWriteRepository.fetchWrite(page).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        myWriteList = state.data
                        _myWriteState.value = 200
                    }
                    is State.ServerError -> {
                        _myWriteState.value = state.code
                    }
                    is State.Error -> {
                        _myWriteError.value = state.exception.message
                    }
                }
            }
        }
    }

    fun getMyWriteList(): List<MyPost>{
        return myWriteList
    }

    fun fetchLike(page: Int){
        viewModelScope.launch {
            memberLikeRepository.fetchLike(page).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        myLikeList = state.data
                        _myLikeState.value = 200
                    }
                    is State.ServerError -> {
                        _myLikeState.value = state.code
                    }
                    is State.Error -> {
                        _myLikeError.value = state.exception.message
                    }
                }
            }
        }
    }

    fun getMyLikeList(): List<MyPost>{
        return myLikeList
    }

    fun resultNick(){
        viewModelScope.launch{
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.nickname.toString())
            _nicknameResponse.value = response.body()?.result?.nickname.toString()
        }
    }

    fun resultImg(){
        viewModelScope.launch {
            val response = checkMemberService.checkMember()
            Log.d(ChatBotActivity.TAG, response.body()?.result?.imageUrl.toString())
            _imgResponse.value = response.body()?.result?.imageUrl.toString()
        }
    }
}