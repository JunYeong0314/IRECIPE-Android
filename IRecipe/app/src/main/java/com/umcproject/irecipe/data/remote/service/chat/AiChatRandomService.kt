package com.umcproject.irecipe.data.remote.service.chat

import com.umcproject.irecipe.data.remote.response.chat.AiChatRandomResponse
import retrofit2.Response
import retrofit2.http.GET

interface AiChatRandomService {
    @GET("/ai-chat/random")
    suspend fun aiChatRandom():Response<AiChatRandomResponse>
}