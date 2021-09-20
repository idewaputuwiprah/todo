package com.example.todo.domain.cases

import com.example.todo.RequestSealed
import com.example.todo.model.Todo

interface SaveTodoUsesCase {
    suspend operator fun invoke(todo: Todo): RequestSealed
}