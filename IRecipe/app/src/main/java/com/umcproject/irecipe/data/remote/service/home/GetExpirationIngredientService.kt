package com.umcproject.irecipe.data.remote.service.home

import com.umcproject.irecipe.data.remote.response.home.expiration.GetExpirationIngredientResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetExpirationIngredientService {
    @GET("/ingredients/expire")
    suspend fun getExpirationIngredient(
        @Query("page") page: Int
    ): Response<GetExpirationIngredientResponse>
}