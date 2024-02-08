package com.umcproject.irecipe.data.remote.service.login

import com.umcproject.irecipe.data.remote.request.LoginRequest
import com.umcproject.irecipe.data.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/members/login")
    suspend fun loginService(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}