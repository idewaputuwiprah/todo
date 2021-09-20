package com.example.todo.di

import com.example.todo.utils.DateUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DateUtilsModule {
    @Provides
    fun provideDateUtils(): DateUtils = DateUtils()
}