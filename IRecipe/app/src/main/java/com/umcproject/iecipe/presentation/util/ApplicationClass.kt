package com.umcproject.iecipe.presentation.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.umcproject.iecipe.BuildConfig

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}