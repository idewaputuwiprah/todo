package com.example.todo.data.todo

import com.example.todo.model.Todo

interface TodoRepository {
    suspend fun saveTodo(todo: Todo): Long
    suspend fun getTodoById(id: Int): Todo
    suspend fun getListTodo(): List<Todo>
}