package com.umcproject.irecipe.data.remote.service.refrigerator

import com.umcproject.irecipe.data.remote.request.refrigerator.RefrigeratorUpdateRequest
import com.umcproject.irecipe.data.remote.response.refrigerator.RefrigeratorUpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface RefrigeratorUpdateService {
    @PATCH("/ingredients/{ingredientId}")
    suspend fun refrigeratorUpadate(
        @Path("ingredientId") ingredientId: Int,
        @Body refrigeratorUpdateRequest: RefrigeratorUpdateRequest
    ):Response<RefrigeratorUpdateResponse>
}