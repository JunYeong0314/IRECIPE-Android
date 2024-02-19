package com.umcproject.irecipe.domain.model

import java.util.Date

data class Refrigerator(
    val type: String,
    val ingredient: List<Ingredient>
)
