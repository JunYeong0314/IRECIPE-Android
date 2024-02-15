package com.umcproject.irecipe.data.remote.service.chat

import com.umcproject.irecipe.data.remote.response.chat.AiChatDislikeResponse
import com.umcproject.irecipe.data.remote.request.chat.AiChatDislikeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AiChatDislikeService {
    @POST("/ai-chat/dislike")
    suspend fun aiChatDislike(
        @Body aiChatDislikeRequest: AiChatDislikeRequest
    ): Response<AiChatDislikeResponse>
}