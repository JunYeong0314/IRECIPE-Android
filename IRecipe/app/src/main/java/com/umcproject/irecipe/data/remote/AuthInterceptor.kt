package com.umcproject.irecipe.data.remote

import com.umcproject.irecipe.domain.repository.UserDataRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


/*class AuthInterceptor @Inject constructor(
    private val userDataRepository: UserDataRepository
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val originRequest = response.request

        if(originRequest.headers("Authorization").isEmpty()){
            return null
        }
        val refreshToken = runBlocking {
            userDataRepository.getUserData().refreshToken
        }

        val refreshRequest = Request.Builder()

    }

}*/
