package com.example.todo.domain.cases

import com.example.todo.RequestSealed

interface GetListUsesCase {
    suspend operator fun invoke(): RequestSealed
}