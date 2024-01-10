package com.umcproject.irecipe.data.module

import androidx.room.Insert
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/* For hilt versions lower than v2.28.2 use ApplicationComponent instead of
SingletonComponent. ApplicationComponent is deprecated and even removed in
some versions above v2.28.2 so better refactor it to SingletonComponent. */

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

}