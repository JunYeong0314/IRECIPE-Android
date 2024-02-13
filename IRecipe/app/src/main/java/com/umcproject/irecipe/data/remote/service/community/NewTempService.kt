package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.NewTempResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewTempService {
    @GET("/post/new-temp")
    suspend fun newPostService(): Response<NewTempResponse>
}