package com.umcproject.irecipe.data.remote.service.chat

import com.umcproject.irecipe.data.remote.response.chat.AiChatRefriResponse
import retrofit2.Response
import retrofit2.http.GET

interface AiChatRefriService {
    @GET("/ai-chat/refri")
    suspend fun aiChatRefriService():Response<AiChatRefriResponse>
}