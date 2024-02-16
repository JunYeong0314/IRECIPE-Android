package com.umcproject.irecipe.data.remote.service.mypage

import com.umcproject.irecipe.data.remote.response.mypage.MemberLikeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberLikeService {
    @GET("/members/liked")
    suspend fun memberLikeService(
        @Query("page") page: Int
    ):Response<MemberLikeResponse>
}