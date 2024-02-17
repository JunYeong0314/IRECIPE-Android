package com.umcproject.irecipe.data.module

import android.content.Context
import com.umcproject.irecipe.data.remote.repository.UserDataRepositoryIml
import com.umcproject.irecipe.data.remote.service.login.GetRefreshTokenService
import com.umcproject.irecipe.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {
    @Singleton
    @Provides
    fun provideUserDataStore(
        @ApplicationContext context: Context
    ): UserDataRepository{
        return UserDataRepositoryIml(context)
    }
}