package com.example.todo.domain.impl

import com.example.todo.RequestSealed
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.GetListUsesCase
import com.example.todo.model.TodoCache
import com.example.todo.utils.DateUtils
import com.example.todo.utils.DateUtils.Companion.FORMAT_DATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GetListImpl(
    private val dateUtils: DateUtils,
    private val todoRepository: TodoRepository
) : GetListUsesCase {
    override suspend fun invoke(): RequestSealed =
        withContext(Dispatchers.IO) {
            val currentDate = dateUtils.getCurrentDate()
            try {
                val response = todoRepository.getListTodo()
                val result = ArrayList<TodoCache>()
                val listExpired = ArrayList<TodoCache>()

                response.map { toDo ->
                    val isDateExpired = isDateExpired(currentDate, toDo.date)
                    val item = TodoCache(
                        id = toDo.id ?: 0,
                        title = toDo.title,
                        description = toDo.description,
                        date = toDo.date,
                    )
                    item.isExpired = isDateExpired
                    if (isDateExpired)
                        listExpired.add(item)
                    else
                        result.add(item)
                }
                if (listExpired.isNotEmpty())
                    result.addAll(listExpired)
                RequestSealed.OnSuccess(result)
            } catch (e: Exception) {
                RequestSealed.OnFailure(e)
            }
        }

    private fun isDateExpired(currentDate: String, date: String): Boolean {
        var simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        return try {
            val newDate = simpleDateFormat.parse(date)
            val currDate = simpleDateFormat.parse(currentDate)
            return newDate.before(currDate)
        }
        catch(e: Exception) {
            try {
                simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val newDate = simpleDateFormat.parse(date)
                val currDate = simpleDateFormat.parse(currentDate)
                return newDate.before(currDate)
            }
            catch (e: Exception) {
                false
            }
        }
    }
}