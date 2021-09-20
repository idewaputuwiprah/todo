package com.example.todo.di

import com.example.todo.data.todo.TodoLocalRepositoryImpl
import com.example.todo.data.todo.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRespository(todoLocalRepositoryImpl: TodoLocalRepositoryImpl): TodoRepository
}