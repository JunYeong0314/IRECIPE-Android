package com.umcproject.irecipe.presentation.util

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.umcproject.eyerecipe.BuildConfig

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        val keyHash = Utility.getKeyHash(this)
        Log.d("해시키", keyHash)
    }
}