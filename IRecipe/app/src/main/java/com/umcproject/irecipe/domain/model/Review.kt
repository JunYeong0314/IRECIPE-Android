package com.umcproject.irecipe.domain.model

import android.net.Uri
import java.text.DateFormat
import java.util.Date

data class Review(
    val reviewId: Int?,
    val writerNick: String?,
    val writerProfile: String?,
    val content: String?,
    val score: Int?,
    val imageUrl: String?,
    val createdAt: String?
)
