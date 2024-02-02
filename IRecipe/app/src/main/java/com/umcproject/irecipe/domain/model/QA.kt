package com.umcproject.irecipe.domain.model

import android.net.Uri
import java.util.Date

data class QA(
    val otherUser: OtherUser,
    val writer: Writer?
)

data class OtherUser(
    val profile: Uri?,
    val name: String,
    val date: Date,
    val content: String
)

data class Writer(
    val profile: Uri?,
    val name: String,
    val date: Date,
    val content: String
)

val mockQAList = listOf(
    QA(OtherUser(null, "스마일", Date(System.currentTimeMillis()), "테스트 질문"), null),
    QA(OtherUser(null, "아이레시피", Date(System.currentTimeMillis()), "테스트 질문"), Writer(null, "작성자", Date(System.currentTimeMillis()), "테스트 답변")),
    QA(OtherUser(null, "유저12", Date(System.currentTimeMillis()), "테스트 질문"), Writer(null, "작성자", Date(System.currentTimeMillis()), "테스트 답변"))
)
