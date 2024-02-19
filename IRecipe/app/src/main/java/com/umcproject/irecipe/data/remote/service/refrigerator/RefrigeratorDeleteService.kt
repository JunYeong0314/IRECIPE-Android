package com.umcproject.irecipe.data.remote.service.refrigerator

import com.umcproject.irecipe.data.remote.response.refrigerator.RefrigeratorDeleteResponse
import com.umcproject.irecipe.domain.model.Ingredient
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface RefrigeratorDeleteService {
    @DELETE("/ingredients/{ingredientId}")
    suspend fun refrigeratorDelete(
        @Path("ingredientId") ingredientId:Int
    ):Response<RefrigeratorDeleteResponse>
}