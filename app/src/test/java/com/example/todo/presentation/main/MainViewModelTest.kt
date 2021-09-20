package com.example.todo.presentation.main

import androidx.lifecycle.Observer
import com.example.todo.InstantExecutorExtension
import com.example.todo.ListTodoSealed
import com.example.todo.RequestSealed
import com.example.todo.domain.TodoService
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    private val todoService = mockk<TodoService>(relaxed = true)
    private val dateUtils = mockk<DateUtils>(relaxed = true)
    private lateinit var mainViewModel: MainViewModel
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()


    @BeforeEach
    fun setup() {
        mainViewModel = MainViewModel(todoService, dateUtils)
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given get todo list, When todoService getList then some list, Then liveData State is ListTodoSealed OnSuccess`() =
        testDispatcher.runBlockingTest {
            //Given
            val list: MutableList<Todo> = ArrayList()
            list.add(Todo(""))

            //Mock
            val observer: Observer<ListTodoSealed> = mockk(relaxed = true)
            coEvery { todoService.getTodoList() } returns RequestSealed.OnSuccess(list)

            //Execute
            mainViewModel.observerList.observeForever(observer)
            mainViewModel.getTodoList()

            //Result
            assert((mainViewModel.observerList.value) is ListTodoSealed.OnSuccess)
            assertSame((mainViewModel.observerList.value as ListTodoSealed.OnSuccess).data, list)
        }

    @Test
    fun `Given observerList is null, When todoService getList throw some error, Then liveData State iListTodoSealed OnFailure`() =
        testDispatcher.runBlockingTest {
            //Given
            val result = NullPointerException()
            mainViewModel.observerList.value = null

            //Mock
            val observer: Observer<ListTodoSealed> = mockk(relaxed = true)
            coEvery { todoService.getTodoList() } returns RequestSealed.OnFailure(result)
            mainViewModel.observerList.observeForever(observer)

            //Execute
            mainViewModel.getTodoList()

            //Result
            assert((mainViewModel.observerList.value) is ListTodoSealed.OnFailure)
            assertSame((mainViewModel.observerList.value as ListTodoSealed.OnFailure).err, result)
        }
}