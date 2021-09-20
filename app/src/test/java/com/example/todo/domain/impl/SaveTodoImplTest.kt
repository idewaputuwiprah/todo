package com.example.todo.domain.impl

import com.example.todo.DateFormatException
import com.example.todo.LatLngException
import com.example.todo.RequestSealed
import com.example.todo.StringEmptyException
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.SaveTodoUsesCase
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class SaveTodoImplTest {

    private val dateUtils = mockk<DateUtils>(relaxed = true)
    private val todoRepository = mockk<TodoRepository>(relaxed = true)
    private lateinit var saveTodo: SaveTodoUsesCase

    @BeforeEach
    fun setup() {
        saveTodo = SaveTodoImpl(dateUtils, todoRepository)
    }

    @Test
    fun `Given save todo, When title is empty, Then throw Throwable must be called`() =
        runBlocking {
            val todo = Todo("")
            //Execute
            val result = saveTodo.invoke(todo)

            //Result
            assert((result as RequestSealed.OnFailure).err is StringEmptyException)
        }

    @Test
    fun `Given save todo, When format date is wrong, Then throw FormatException is called`() =
        runBlocking {
            //Given
            val todo = Todo("title", "2021-01-01")
            todo.latitude = -6.2
            todo.longitude = 106.0223423

            //Mock
            every { dateUtils.checkFormatDate(todo.date) } returns false

            //Execute
            val result = saveTodo.invoke(todo)

            //Result
            assert((result as RequestSealed.OnFailure).err is DateFormatException)
        }

    @Test
    fun `Given save todo, When format date is not wrong and title is not empty, Then return todo id`() =
        runBlocking {
            //Given
            val milis = 345353L
            val todo = Todo("title", "2021-01-01")
            todo.latitude = -6.2
            todo.longitude = 106.0223423

            //Mock
            every { dateUtils.checkFormatDate(todo.date) } returns true
            every { dateUtils.getMilisByDate(todo.date) } returns milis
            coEvery { todoRepository.saveTodo(todo) } returns 1

            //Execute
            val result = saveTodo.invoke(todo)

            //Result
            assertEquals((result as RequestSealed.OnSuccess<*>).data, 1L)
        }

    @Test
    fun `Given save todo, When lat and lng is 0, Then return throw NumberException`() =
        runBlocking {
            //Given
            val todo = Todo("title", "2021-01-01")

            //Mock
            every { dateUtils.checkFormatDate(todo.date) } returns false

            //Execute
            val result = saveTodo.invoke(todo)

            //Result
            assert((result as RequestSealed.OnFailure).err is LatLngException)
        }
}