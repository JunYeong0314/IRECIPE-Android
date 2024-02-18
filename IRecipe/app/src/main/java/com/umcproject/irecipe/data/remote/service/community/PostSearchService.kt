package com.umcproject.irecipe.data.remote.service.community

import com.umcproject.irecipe.data.remote.response.community.post.PostSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostSearchService {
    @GET("/post/search")
    suspend fun postSearch(
        @Query("page") page: Int,
        @Query("keyword") keyword: String,
        @Query("type") type: String
    ):Response<PostSearchResponse>
}