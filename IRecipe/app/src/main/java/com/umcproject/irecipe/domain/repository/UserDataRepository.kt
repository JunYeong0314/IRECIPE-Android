package com.umcproject.irecipe.domain.repository

import com.umcproject.irecipe.domain.model.User

interface UserDataRepository {
    suspend fun getUserData(): User
    suspend fun setUserData(key: String, value: String)
}