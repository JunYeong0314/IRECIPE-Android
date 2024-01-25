package com.umcproject.irecipe.domain.model

import android.net.Uri

data class User(
    var num: String = "",
    var name: String = "",
    var gender: String = "",
    var age: String = "",
    var photoUri: Uri? = null,
    var nick: String = "",
    var allergy: String = ""
)
