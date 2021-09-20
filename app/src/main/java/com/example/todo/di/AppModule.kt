package com.example.todo.di

import com.example.todo.domain.TodoService
import com.example.todo.domain.TodoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideTodoService(todoServiceImpl: TodoServiceImpl): TodoService
}