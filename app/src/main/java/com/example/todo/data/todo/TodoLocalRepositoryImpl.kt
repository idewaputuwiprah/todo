package com.example.todo.data.todo

import com.example.todo.database.dao.TodoDao
import com.example.todo.model.Todo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoLocalRepositoryImpl @Inject constructor(private val todoDao: TodoDao) : TodoRepository {
    override suspend fun saveTodo(todo: Todo): Long {
        return todoDao.saveTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo {
        return todoDao.getTodoById(id)
    }

    override suspend fun getListTodo(): List<Todo> {
        return todoDao.getListTodo()
    }

}