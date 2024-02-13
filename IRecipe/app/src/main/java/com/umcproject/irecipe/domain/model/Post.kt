package com.umcproject.irecipe.domain.model

import android.net.Uri

data class Post(
    var num: String = "",
//    var time: String = "",
    var title: String = "",
    var subtitle: String = "", // subhead
    var text: String = "", //content
//    var name: String = "",
    var postImgUri: Uri? = null,
    var level: String = "",
    var category: String = ""
//    var profileImg: Int = 0,

//    var heart: Int = 0,
//    var star: Int = 0,
//    var comment: Int
)
