package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Ingredient2
import com.umcproject.irecipe.domain.model.Refrigerator
import kotlinx.coroutines.flow.Flow

interface RefrigeratorRepository {
    fun setIngredient(ingredient: Ingredient2): Flow<State<Int>> // 냉장고 재료 넣기
    fun fetchIngredientType(type: String, page: Int): Flow<State<Refrigerator>> // type에 따른 재료 불러오기
    fun searchIngredient(food:String): Flow<State<List<Ingredient>>> // 검색해서 type에 따른 재료 불러오기
    fun deleteIngredient(ingredientId:Int): Flow<State<Int>> //냉장고 재료 삭제
    fun updateIngredient(ingredientId:Int, ingredient: Ingredient2): Flow<State<Int>>
    fun fetchExpirationIngredient(page: Int): Flow<State<List<Ingredient>>> // 유통기한 순으로 재료 불러오기
}