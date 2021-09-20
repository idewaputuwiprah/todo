package com.example.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.database.dao.TodoDao
import com.example.todo.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}