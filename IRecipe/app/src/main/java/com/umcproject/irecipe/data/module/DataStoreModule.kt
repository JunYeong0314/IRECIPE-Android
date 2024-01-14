package com.umcproject.irecipe.data.module

import android.content.Context
import com.umcproject.irecipe.data.remote.repository.UserDataRepositoryIml
import com.umcproject.irecipe.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context
    ): UserDataRepository{
        return UserDataRepositoryIml(context)
    }
}