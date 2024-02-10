package com.umcproject.irecipe.data.remote

import android.util.Log
import com.umcproject.irecipe.domain.repository.UserDataRepository
import com.umcproject.irecipe.presentation.util.ApplicationClass
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AppInterceptor @Inject constructor(
    private val userDataRepository: UserDataRepository
): Interceptor {
    @Throws
    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = runBlocking {
            "Bearer " + userDataRepository.getUserData().accessToken
        }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", authToken)
            .build()

        return chain.proceed(request)
    }
}
