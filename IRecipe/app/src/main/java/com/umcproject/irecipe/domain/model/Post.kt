package com.umcproject.irecipe.domain.model

data class Post(
    var time: String = "",
    var title: String = "",
    var subtitle: String = "",
    var text: String = "",
    var writer: String = "",
    var image: Int = 0
)
