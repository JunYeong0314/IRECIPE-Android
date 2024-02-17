package com.umcproject.irecipe.domain.model

import android.net.Uri

data class SetReview(
    var content: String = "",
    var score: Int = 0,
    var imageUri: Uri? = null
)