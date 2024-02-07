package com.umcproject.irecipe.domain.service.login

import com.umcproject.irecipe.domain.request.SignUpRequest
import com.umcproject.irecipe.domain.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/members/signup")
    suspend fun signIn(
        @Body body: SignUpRequest
    ): Response<SignUpResponse>
}