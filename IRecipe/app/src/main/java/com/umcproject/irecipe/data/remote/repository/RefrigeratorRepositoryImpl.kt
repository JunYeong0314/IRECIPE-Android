package com.umcproject.irecipe.data.remote.repository

import android.util.Log
import com.umcproject.irecipe.data.remote.request.refrigerator.SetRefrigeratorRequest
import com.umcproject.irecipe.data.remote.service.refrigerator.GetRefrigeratorService
import com.umcproject.irecipe.data.remote.service.refrigerator.SetRefrigeratorService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.sql.Ref

class RefrigeratorRepositoryImpl(
    private val getRefrigeratorService: GetRefrigeratorService,
    private val setRefrigeratorService: SetRefrigeratorService
): RefrigeratorRepository {
    private var refrigeratorList = emptyList<Refrigerator>()

    override fun fetchRefrigerator(page: Int): Flow<State<Int>> = flow{
        emit(State.Loading)

        val response = getRefrigeratorService.getRefrigerator(page)
        val statusCode = response.code()

        if(statusCode == 200){
            val refList = response.body()?.result?.ingredientList

            if(!refList.isNullOrEmpty()){
                refrigeratorList = refList.mapNotNull { ingredient ->
                    Refrigerator(
                        type = ingredient?.type!!,
                        ingredient = listOf(Ingredient(
                            name = ingredient.name!!,
                            type = ingredient.type,
                            expiration = ingredient.expiryDate!!,
                            category = ingredient.category!!,
                            memo = ingredient.memo ?: ""
                        ))
                    )
                }
                Log.d("TEST", refrigeratorList.toString())
                emit(State.Success(statusCode))
            }
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun setIngredient(ingredient: Ingredient): Flow<State<Int>> = flow{
        emit(State.Loading)

        val request = SetRefrigeratorRequest(
            name = ingredient.name,
            type = ingredient.type,
            memo = ingredient.memo,
            expiryDate = ingredient.expiration,
            category = ingredient.category
        )
        request.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val response = setRefrigeratorService.setRefrigerator(request)
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun getNormalIngredient(): Refrigerator {
        val normalIngredients = refrigeratorList.find { it.type == "AMBIENT" }?.ingredient.orEmpty()
        return Refrigerator(type = "AMBIENT", ingredient = normalIngredients)
    }

    override fun getColdIngredient(): Refrigerator {
        val coldIngredients = refrigeratorList.find { it.type == "REFRIGERATED" }?.ingredient.orEmpty()
        return Refrigerator(type = "REFRIGERATED", ingredient = coldIngredients)
    }

    override fun getFrozenIngredient(): Refrigerator {
        val frozenIngredients = refrigeratorList.find { it.type == "FROZEN" }?.ingredient.orEmpty()
        return Refrigerator(type = "FROZEN", ingredient = frozenIngredients)
    }

}