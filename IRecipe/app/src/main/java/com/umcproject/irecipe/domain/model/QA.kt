package com.umcproject.irecipe.domain.model

import android.net.Uri
import java.util.Date

data class QA(
    val writer: Writer?,
    val otherUser: OtherUser,
)

data class OtherUser(
    val nick: String?,
    val profileUrl: String?,
    val createdAt: String?,
    val content: String?
)

data class Writer(
    val nick: String?,
    val profileUrl: String?,
    val createdAt: String?,
    val content: String?
)
