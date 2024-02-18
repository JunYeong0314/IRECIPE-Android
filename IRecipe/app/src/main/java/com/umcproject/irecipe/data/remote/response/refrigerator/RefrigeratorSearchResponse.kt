package com.umcproject.irecipe.data.remote.response.refrigerator

import com.squareup.moshi.Json

data class RefrigeratorSearchResponse(
    @field:Json(name = "code")
    val code: String?,
    @field:Json(name = "isSuccess")
    val isSuccess: Boolean?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "result")
    val result: RefriSearchResult?
)
data class RefriSearchResult(
    @field:Json(name = "isFirst")
    val isFirst: Boolean?,
    @field:Json(name = "isLast")
    val isLast: Boolean?,
    @field:Json(name = "listSize")
    val listSize: Int?,
    @field:Json(name = "ingredientList")
    val ingredientList: List<Ingredient>?,
    @field:Json(name = "totalElements")
    val totalElements: Int?,
    @field:Json(name = "totalPage")
    val totalPage: Int?
)