package com.umcproject.irecipe.domain.model

import java.util.Date

data class Ingredient(
    val name: String,
    val category: String,
    val saveInfo: String,
    val expiration: Date,
    val memo: String
)


