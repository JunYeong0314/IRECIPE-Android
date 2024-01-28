package com.umcproject.irecipe.domain.model

data class Chat(
    var chat: String?,
    var sendId: String
){
    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}
