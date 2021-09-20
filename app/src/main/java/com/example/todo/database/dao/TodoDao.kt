package com.example.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.todo.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getListTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoById(id: Int): Todo

    @Insert(onConflict = REPLACE)
    fun saveTodo(todo: Todo): Long

}