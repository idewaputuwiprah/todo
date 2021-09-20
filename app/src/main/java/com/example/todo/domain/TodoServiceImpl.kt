package com.example.todo.domain

import com.example.todo.RequestSealed
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.impl.GetListImpl
import com.example.todo.domain.impl.GetTodoByIdImpl
import com.example.todo.domain.impl.SaveTodoImpl
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import javax.inject.Inject

class TodoServiceImpl @Inject constructor(
    private val todoRepository: TodoRepository,
) : TodoService {
    override suspend fun getTodoList(): RequestSealed =
        GetListImpl(DateUtils(), todoRepository).invoke()

    override suspend fun getTodoById(id: Int): RequestSealed =
        GetTodoByIdImpl(DateUtils(), todoRepository).invoke(id)

    override suspend fun saveTodo(todo: Todo): RequestSealed =
        SaveTodoImpl(DateUtils(), todoRepository).invoke(todo)
}