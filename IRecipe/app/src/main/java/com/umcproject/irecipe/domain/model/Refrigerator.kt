package com.umcproject.irecipe.domain.model

import java.util.Date

data class Refrigerator(
    val title: String,
    val ingredient: List<Ingredient>
)
val mockList: List<Ingredient> = listOf(
    Ingredient(name = "마요네즈", category = "재료", saveInfo = "실온", expiration = Date(20230903), memo = ""),
    Ingredient(name = "마요네즈", category = "재료", saveInfo = "실온", expiration = Date(20230903), memo = ""),
    Ingredient(name = "마요네즈", category = "재료", saveInfo = "실온", expiration = Date(20230903), memo = "")
)

val mockData: List<Refrigerator> = listOf(
    Refrigerator(title = "실온보관", ingredient = mockList),
    Refrigerator(title = "실온보관", ingredient = mockList),
    Refrigerator(title = "실온보관", ingredient = mockList),
)

