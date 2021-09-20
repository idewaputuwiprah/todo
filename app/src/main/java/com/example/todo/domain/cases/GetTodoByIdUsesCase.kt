package com.example.todo.domain.cases

import com.example.todo.RequestSealed

interface GetTodoByIdUsesCase {
    suspend operator fun invoke(id: Int): RequestSealed
}