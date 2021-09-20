package com.example.todo.model

class TodoCache(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
) {
    var isExpired: Boolean = false
}