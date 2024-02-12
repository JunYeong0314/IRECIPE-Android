package com.umcproject.irecipe.data.remote.service.chat

import com.umcproject.irecipe.data.remote.response.chat.AiChatExpiryResponse
import retrofit2.Response
import retrofit2.http.GET

interface AiChatExpiryService {
    @GET("/ai-chat/expiry")
    suspend fun aiChatExpiryService():Response<AiChatExpiryResponse>
}