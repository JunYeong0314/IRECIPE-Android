package com.umcproject.irecipe.data.remote.service.mypage

import com.umcproject.irecipe.data.remote.response.mypage.MemberWriteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberWriteService {
    @GET("/members/written")
    suspend fun memberWriteService(
        @Query("page") page: Int
    ):Response<MemberWriteResponse>
}