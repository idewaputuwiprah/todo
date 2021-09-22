package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.database.Database
import com.example.todo.database.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(database: Database): TodoDao = database.todoDao()
}