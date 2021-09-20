package com.example.todo.domain.impl

import com.example.todo.DateFormatException
import com.example.todo.LatLngException
import com.example.todo.RequestSealed
import com.example.todo.StringEmptyException
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.SaveTodoUsesCase
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils

class SaveTodoImpl(
    private val dateUtils: DateUtils,
    private val todoLocalRepository: TodoRepository
) : SaveTodoUsesCase {


    override suspend fun invoke(todo: Todo): RequestSealed {
        return try {
            if (todo.title == "") throw StringEmptyException()
            if (todo.latitude == 0.0 && todo.longitude == 0.0) throw LatLngException()
            if (!dateUtils.checkFormatDate(todo.date)) throw DateFormatException()
            val result = todoLocalRepository.saveTodo(todo)
            RequestSealed.OnSuccess(result)
        } catch (e: Throwable) {
            RequestSealed.OnFailure(e)
        }
    }
}