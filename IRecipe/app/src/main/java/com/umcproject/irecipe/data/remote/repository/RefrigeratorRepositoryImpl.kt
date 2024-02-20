package com.umcproject.irecipe.data.remote.repository

import com.umcproject.irecipe.data.remote.request.refrigerator.RefrigeratorUpdateRequest
import com.umcproject.irecipe.data.remote.request.refrigerator.SetRefrigeratorRequest
import com.umcproject.irecipe.data.remote.service.home.GetExpirationIngredientService
import com.umcproject.irecipe.data.remote.service.refrigerator.GetTypeIngredientService
import com.umcproject.irecipe.data.remote.service.refrigerator.RefrigeratorDeleteService
import com.umcproject.irecipe.data.remote.service.refrigerator.RefrigeratorSearchService
import com.umcproject.irecipe.data.remote.service.refrigerator.RefrigeratorUpdateService
import com.umcproject.irecipe.data.remote.service.refrigerator.SetRefrigeratorService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Ingredient2
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class RefrigeratorRepositoryImpl(
    private val setRefrigeratorService: SetRefrigeratorService,
    private val getTypeIngredientService: GetTypeIngredientService,
    private val refrigeratorSearchService: RefrigeratorSearchService,
    private val refrigeratorDeleteService: RefrigeratorDeleteService,
    private val refrigeratorUpdateService: RefrigeratorUpdateService,
    private val getExpirationIngredientService: GetExpirationIngredientService
): RefrigeratorRepository {
    override fun setIngredient(ingredient: Ingredient2): Flow<State<Int>> = flow{
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

    override fun fetchIngredientType(type: String, page: Int): Flow<State<Refrigerator>> = flow{
        emit(State.Loading)

        val response = getTypeIngredientService.getTypeIngredient(
            page = page, type = type
        )
        val statusCode = response.code()

        if(statusCode == 200){
            val responseBody = response.body()?.result?.ingredientList
            val ingredient = responseBody?.mapNotNull {
                Ingredient(
                    name = it?.name,
                    category = it?.category,
                    expiration = it?.expiryDate,
                    memo = it?.memo,
                    type = it?.type,
                    id = it?.ingredientId,
                    remainDay = it?.remainingDays
                )
            } ?: emptyList()

            emit(State.Success(Refrigerator(type, ingredient)))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun searchIngredient(food:String): Flow<State<List<Ingredient>>> = flow{
        emit(State.Loading)

        val response = refrigeratorSearchService.refrigeratorSearch(
            name = food, page = 0
        )
        val statusCode = response.code()

        if(statusCode == 200){
            val responseBody = response.body()?.result?.ingredientList

            val ingredient = responseBody?.map {
                Ingredient(
                    name = it.name,
                    category = it.category,
                    expiration = it.expiryDate,
                    memo = it.memo,
                    type = it.type,
                    id = it.ingredientId,
                    remainDay = it.remainingDays
                )
            } ?: emptyList()
            emit(State.Success(ingredient))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun deleteIngredient(ingredientId: Int): Flow<State<Int>> = flow{
        emit(State.Loading)

        val response = refrigeratorDeleteService.refrigeratorDelete(ingredientId)
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun updateIngredient(ingredientId: Int, ingredient: Ingredient2): Flow<State<Int>> = flow{
        emit(State.Loading)

        val request = RefrigeratorUpdateRequest(
            name = ingredient.name,
            type = ingredient.type,
            memo = ingredient.memo,
            expiryDate = ingredient.expiration,
            category = ingredient.category
        )
        request.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val response = refrigeratorUpdateService.refrigeratorUpadate(ingredientId, request)
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun fetchExpirationIngredient(page: Int): Flow<State<List<Ingredient>>> = flow{
        emit(State.Loading)

        val response = getExpirationIngredientService.getExpirationIngredient(page)
        val statusCode = response.code()

        if(statusCode == 200){
            val result = response.body()?.result?.ingredientList?.mapNotNull {
                Ingredient(
                    name = it?.name,
                    category = it?.category,
                    expiration = it?.expiryDate,
                    type = it?.type,
                    memo = it?.memo,
                    id = it?.ingredientId,
                    remainDay = it?.remainingDays
                )
            } ?: emptyList()

            emit(State.Success(result))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }
}