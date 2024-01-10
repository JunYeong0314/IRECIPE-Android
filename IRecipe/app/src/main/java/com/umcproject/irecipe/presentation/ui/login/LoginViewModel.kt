package com.umcproject.irecipe.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navercorp.nid.oauth.NidOAuthLogin
import com.umcproject.irecipe.presentation.ui.login.manager.KakaoLoginManager
import com.umcproject.irecipe.presentation.ui.login.manager.NaverLoginManager
import com.umcproject.irecipe.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(
    context: Context
): ViewModel() {
    private val naverLoginManager = NaverLoginManager(context)
    private val kakaoLoginManager = KakaoLoginManager(context)

    private val _isLogin = MutableStateFlow<Boolean?>(null)
    val isLogin: StateFlow<Boolean?>
        get() = _isLogin.asStateFlow()

    // 소셜에 따른 로그인 처리
    fun startLogin(platform: String){
        if(platform == "naver"){
            naverLoginManager.startLogin { token->
                viewModelScope.launch {
                    if(token != ""){
                        _isLogin.emit(true)
                    }else{
                        _isLogin.emit(false)
                    }
                }
            }
        }else if(platform == "kakao"){
            kakaoLoginManager.startKakaoLogin { token->
                viewModelScope.launch {
                    if(token != ""){
                        _isLogin.emit(true)
                    }else{
                        _isLogin.emit(false)
                    }
                }
            }
        }
    }
}