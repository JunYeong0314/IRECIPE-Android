package com.umcproject.irecipe.domain.model

import android.net.Uri

data class Post(
    var title: String = "",
    var subtitle: String = "", // subhead
    var content: String = "", //content
    var postImgUri: Uri? = null,
    var level: String = "",
    var category: String = ""
)
