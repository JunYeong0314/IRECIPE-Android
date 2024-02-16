package com.umcproject.irecipe.data.remote.service.login

import com.umcproject.irecipe.data.remote.response.login.token.GetRefreshTokenResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface GetRefreshTokenService {
    @POST("/members/refresh")
    suspend fun getRefreshToken(
        @Query("personal id") personalId: String
    ): Response<GetRefreshTokenResponse>
}