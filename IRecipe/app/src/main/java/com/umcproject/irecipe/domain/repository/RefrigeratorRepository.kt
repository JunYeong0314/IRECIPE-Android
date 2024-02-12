package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Refrigerator
import kotlinx.coroutines.flow.Flow

interface RefrigeratorRepository {
    fun fetchRefrigerator(page: Int): Flow<State<Int>> // 냉장고 재료 불러오기
    fun setIngredient(ingredient: Ingredient): Flow<State<Int>> // 냉장고 재료 넣기
    fun getNormalIngredient(): Refrigerator // 실온보관 재료 불러오기
    fun getColdIngredient(): Refrigerator // 냉장보관 재료 불러오기
    fun getFrozenIngredient(): Refrigerator  // 냉동보관 재료 불러오기
}