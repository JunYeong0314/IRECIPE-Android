package com.umcproject.irecipe.domain.model

import android.net.Uri
import java.text.DateFormat
import java.util.Date

data class Review(
    val profile: Uri?,
    val name: String,
    val photo: Uri?,
    val date: Date,
    val rating: Double,
    val content: String
)

val mockReviewList = listOf(
    Review(null, "익명", null, Date(System.currentTimeMillis()), 5.0, "테스트")
)