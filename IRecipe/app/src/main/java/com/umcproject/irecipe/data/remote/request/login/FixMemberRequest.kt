package com.umcproject.irecipe.data.remote.request.login

import com.squareup.moshi.Json

data class FixMemberRequest(
    @field:Json(name = "activity")
    val activity: Boolean?,
    @field:Json(name = "age")
    val age: Int?,
    @field:Json(name = "allergyList")
    val allergyList: List<Int>?,
    @field:Json(name = "event")
    val event: Boolean?,
    @field:Json(name = "gender")
    val gender: Int?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "important")
    val important: Boolean?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "nickname")
    val nickname: String?
)