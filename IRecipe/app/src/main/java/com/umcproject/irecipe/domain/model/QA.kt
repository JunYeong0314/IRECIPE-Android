package com.umcproject.irecipe.domain.model

import android.net.Uri
import java.util.Date

data class QA(
    val qnaId: Int?,
    val writer: Writer?,
    val otherUser: List<OtherUser>?,
)

data class OtherUser(
    val nick: String?,
    val createdAt: String?,
    val content: String?
)

data class Writer(
    val nick: String?,
    val imageUrl: String?,
    val createdAt: String?,
    val content: String?
)
