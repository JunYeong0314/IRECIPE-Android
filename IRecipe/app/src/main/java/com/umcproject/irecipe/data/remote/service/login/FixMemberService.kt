package com.umcproject.irecipe.data.remote.service.login

import com.umcproject.irecipe.data.remote.request.login.FixMemberRequest
import com.umcproject.irecipe.data.remote.response.login.FixMemberResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface FixMemberService {
    @PATCH("/members/fix/info")
    suspend fun fixMember(
        @Body fixMemberRequest: FixMemberRequest
    ): Response<FixMemberResponse>
}