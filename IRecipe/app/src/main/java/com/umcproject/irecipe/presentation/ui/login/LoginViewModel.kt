package com.umcproject.irecipe.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.umcproject.irecipe.data.remote.repository.UserDataRepositoryIml
import com.umcproject.irecipe.domain.repository.UserDataRepository
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
    context: Context,
    private val userDataRepository: UserDataRepository
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
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse>{
                    override fun onError(errorCode: Int, message: String) {}
                    override fun onFailure(httpStatus: Int, message: String) {}
                    override fun onSuccess(result: NidProfileResponse) {
                        viewModelScope.launch{
                            userDataRepository.setUserData("num", result.profile?.id.toString())
                            _isLogin.emit(true)
                        }
                    }

                })
            }
        }else if(platform == "kakao"){
            kakaoLoginManager.startKakaoLogin { token->
                UserApiClient.instance.me { user, _ ->
                    viewModelScope.launch {
                        userDataRepository.setUserData("num", user?.id.toString())
                        _isLogin.emit(true)
                    }
                }
            }
        }
    }
}