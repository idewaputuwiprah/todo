package com.example.todo.domain

import com.example.todo.RequestSealed
import com.example.todo.model.Todo

interface TodoService {
    suspend fun getTodoList(): RequestSealed
    suspend fun getTodoById(id: Int): RequestSealed
    suspend fun saveTodo(todo: Todo): RequestSealed
}