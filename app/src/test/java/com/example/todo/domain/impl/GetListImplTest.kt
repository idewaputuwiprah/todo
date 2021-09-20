package com.example.todo.domain.impl

import com.example.todo.RequestSealed
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.GetListUsesCase
import com.example.todo.model.Todo
import com.example.todo.model.TodoCache
import com.example.todo.utils.DateUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetListImplTest {
    private val todoRepository = mockk<TodoRepository>(relaxed = true)
    private val dateUtils = mockk<DateUtils>(relaxed = true)
    private lateinit var getTodoList: GetListUsesCase

    @BeforeEach
    fun setup() {
        getTodoList = GetListImpl(dateUtils, todoRepository)
    }

    @Test
    fun `Given today, When value is expired, Then todo expired must be last of list`() =
        runBlocking {

            //GIVEN
            val today = "2021-01-03"
            val todoExpired = Todo("title", "2021-01-01")
            todoExpired.description = "sample"
            todoExpired.id = 1
            val tempList: MutableList<Todo> = ArrayList()
            tempList.add(todoExpired)
            for (i in 5 until 9) {
                val temp = Todo("title $i", "2021-01-0$i")
                temp.description = "sample"
                temp.id = i
                tempList.add(temp)
            }

            //Mock
            every { dateUtils.getCurrentDate() } returns today
            coEvery { todoRepository.getListTodo() } returns tempList

            //Execute
            val result = getTodoList.invoke()

            //Result
            val data = result as RequestSealed.OnSuccess<*>
            assertEquals((data.data as List<TodoCache>).last().date, todoExpired.date)
        }

    @Test
    fun `Given today, When todoRepository throw Throwable, then catch Throwable `() =
        runBlocking {
            //Given
            val today = "2021-01-03"
            val expected = NullPointerException()

            //Mock
            every { dateUtils.getCurrentDate() } returns today
            coEvery { todoRepository.getListTodo() }.throws(expected)

            //Execute
            val result = getTodoList.invoke()

            //Result
            assertEquals((result as RequestSealed.OnFailure).err, expected)
        }
}