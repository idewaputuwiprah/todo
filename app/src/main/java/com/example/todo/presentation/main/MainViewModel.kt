package com.example.todo.presentation.main

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.CreateTodoSealed
import com.example.todo.ListTodoSealed
import com.example.todo.RequestSealed
import com.example.todo.domain.TodoService
import com.example.todo.model.Todo
import com.example.todo.model.TodoCache
import com.example.todo.presentation.main.fragment.date.DatePickerFragment
import com.example.todo.presentation.main.fragment.date.DatePickerListener
import com.example.todo.presentation.main.fragment.date.TimePickerFragment
import com.example.todo.presentation.main.fragment.date.TimePickerListener
import com.example.todo.utils.DateUtils
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoService: TodoService,
    private val dateUtils: DateUtils
) : ViewModel() {

    var supportFragmentManager: FragmentManager? = null

    private val mTodoCacheLiveData: MutableLiveData<ListTodoSealed> = MutableLiveData()
    val observerList get() = mTodoCacheLiveData

    private val mTodoState: MutableLiveData<CreateTodoSealed> = MutableLiveData()
    val observerSave get() = mTodoState

    private val mTitleLiveData: MutableLiveData<String> = MutableLiveData()
    val observerTitle get() = mTitleLiveData

    private val mDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
    val observerDescription get() = mDescriptionLiveData

    private val mDateLiveData: MutableLiveData<String> = MutableLiveData()
    val observerDate get() = mDateLiveData

    private val mLatLngLiveData: MutableLiveData<LatLng> = MutableLiveData()
    val observerLatLng get() = mLatLngLiveData

    fun getTodoList() {
        mTodoCacheLiveData.value = ListTodoSealed.Progress
        viewModelScope.launch {
            when (val request = todoService.getTodoList()) {
                is RequestSealed.OnSuccess<*> -> {
                    mTodoCacheLiveData.value = ListTodoSealed.OnSuccess(request.data as List<TodoCache>)
//                    mTodoCacheLiveData.postValue(ListTodoSealed.OnSuccess(request.data as List<TodoCache>))
                }
                is RequestSealed.OnFailure -> {
                    mTodoCacheLiveData.value = ListTodoSealed.OnFailure(request.err)
//                    mTodoCacheLiveData.postValue(ListTodoSealed.OnFailure(request.err))
                }
            }
        }
    }

    fun getTodoById(id: Int) {
        mTodoState.value = CreateTodoSealed.OnProgressGet
        viewModelScope.launch(Dispatchers.IO) {
            when (val request = todoService.getTodoById(id)) {
                is RequestSealed.OnSuccess<*> -> {
                    val data = request.data as Todo
                    val coordinate = LatLng(data.latitude, data.longitude)
                    mTodoState.postValue(CreateTodoSealed.OnGetSuccess(data))
                    mTitleLiveData.postValue(data.title)
                    mDescriptionLiveData.postValue(data.description)
                    mDateLiveData.postValue(data.date)
                    mLatLngLiveData.postValue(coordinate)
                }
            }
        }
    }

    private fun saveTodo(todo: Todo) {
        mTodoState.value = CreateTodoSealed.OnProgressSave
        todo.apply {
            title = mTitleLiveData.value ?: ""
            date = mDateLiveData.value ?: date
            description = mDescriptionLiveData.value ?: ""
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val request = todoService.saveTodo(todo)) {
                is RequestSealed.OnSuccess<*> -> {
                    mTodoState.postValue(CreateTodoSealed.OnSaveSuccess)
                }
                is RequestSealed.OnFailure -> {
                    Log.d("MainViewModel", request.err.toString())
                }
            }
        }
    }

    fun getCalender(view: View) {
        val datePicker = DatePickerFragment().also {
            it.setListener(object : DatePickerListener {
                override fun onDialogDateSet(year: Int, month: Int, dayOfMonth: Int) {
                    getHours(year, month, dayOfMonth)
                }
            })
        }
        supportFragmentManager?.let {
            datePicker.show(it, "datePicker")
        }
    }

    private fun getHours(year: Int, month: Int, dayOfMonth: Int) {
        val timePicker = TimePickerFragment().also {
            it.setListener(object : TimePickerListener {
                override fun onDialogTimeSet(hourOfDay: Int, minute: Int) {
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth, hourOfDay, minute)
                    mDateLiveData.value = dateUtils.getDateWithFormatFromTimeMilis(calendar.timeInMillis)
                }
            })
        }
        supportFragmentManager?.let {
            timePicker.show(it, "timePicker")
        }
    }

    fun submit(todo: Todo) {
        saveTodo(todo)
    }
}