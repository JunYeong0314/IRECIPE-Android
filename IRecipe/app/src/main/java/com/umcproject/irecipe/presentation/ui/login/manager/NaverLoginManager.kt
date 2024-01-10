package com.umcproject.irecipe.presentation.ui.login.manager

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.umcproject.eyerecipe.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NaverLoginManager(
    private val context: Context
) {
    companion object {
        const val TAG = "NaverLoginManager"
        const val NAVER_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val NAVER_CLIENT_KEY = BuildConfig.NAVER_CLIENT_KEY
    }

    lateinit var oAuthLoginCallback: OAuthLoginCallback
        private set

    // OAuthLogin 콜백 함수
    fun naverSetOAuthLoginCallback(updateSocialToken: (String) -> Unit){
        oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, message)
            }
            override fun onSuccess() {
                updateSocialToken(NaverIdLoginSDK.getAccessToken() ?: "")
            }
        }
    }

    // 실제 로그인 시작 함수
    fun startLogin(updateSocialToken: (String) -> Unit) {
        naverSetOAuthLoginCallback {
            updateSocialToken(it)
        }
        // 초기화 작업
        NaverIdLoginSDK.initialize(
            context,
            NAVER_CLIENT_ID,
            NAVER_CLIENT_KEY,
            "캐치플랜"
        )
        NaverIdLoginSDK.authenticate(context, oAuthLoginCallback)
    }
}