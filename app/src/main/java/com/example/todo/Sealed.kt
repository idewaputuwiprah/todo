package com.example.todo

import com.example.todo.model.Todo
import com.example.todo.model.TodoCache

/**
 * Diharapkan untuk tidak melakukan perubahan pada file ini
 * **/

sealed class RequestSealed {
    class OnSuccess<T>(val data: T) : RequestSealed()
    class OnFailure(val err: Throwable) : RequestSealed()
}

sealed class ListTodoSealed {
    object Progress : ListTodoSealed()
    class OnSuccess(val data: List<TodoCache>) : ListTodoSealed()
    class OnFailure(val err: Throwable) : ListTodoSealed()
}

sealed class CreateTodoSealed {
    object OnProgressSave : CreateTodoSealed()
    object OnProgressGet : CreateTodoSealed()
    object OnSaveSuccess : CreateTodoSealed()
    class OnGetSuccess(val todo: Todo?) : CreateTodoSealed()
    class OnFailure(val err: Throwable) : CreateTodoSealed()
}