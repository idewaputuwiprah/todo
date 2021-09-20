package com.example.todo.domain.impl

import com.example.todo.RequestSealed
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.GetTodoByIdUsesCase
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetTodoByIdImplTest {
    private val dateUtils = mockk<DateUtils>(relaxed = true)
    private val todoRepository = mockk<TodoRepository>(relaxed = true)
    private lateinit var getTodoByIdImpl: GetTodoByIdUsesCase

    @BeforeEach
    fun setup() {
        getTodoByIdImpl = GetTodoByIdImpl(dateUtils, todoRepository)
    }

    @Test
    fun `Given getTodo, When id is bigger than 0, Then result todo must have id, title and latlng`() =
        runBlocking {
            //Given
            val todo = Todo("title", "2021-03-02")
            todo.id = 1

            //Mock
            coEvery { todoRepository.getTodoById(1) } returns todo

            //Execute
            val result = getTodoByIdImpl.invoke(1)

            val todoResult = (result as RequestSealed.OnSuccess<Todo>).data
            assertEquals(todoResult.title, "title")
            assertEquals(todoResult.date, "2021-03-02")
            assertEquals(todoResult.id, 1)
        }

    @Test
    fun `Given getTodo, When id is less than 0, Then result todo must have date only`() =
        runBlocking {
            //Given
            val currentDate = "2021-03-02"

            //Mock
            every { dateUtils.getCurrentDate() } returns currentDate

            //Execute
            val result = getTodoByIdImpl.invoke(-1)

            val todoResult = (result as RequestSealed.OnSuccess<Todo>).data
            assertEquals(todoResult.title, "")
            assertEquals(todoResult.date, currentDate)
            assertEquals(todoResult.id, null)
        }

    @Test
    fun `Given todo, When todoRepository throw Throwable, Then result throw Throwable`() =
        runBlocking {
            //Given
            val throwable = Throwable()

            //Mock
            coEvery { todoRepository.getTodoById(1) }.throws(throwable)

            //Execute
            val result = getTodoByIdImpl.invoke(1)

            val todoResult = (result as RequestSealed.OnFailure).err
            assertEquals(todoResult, throwable)
        }

}