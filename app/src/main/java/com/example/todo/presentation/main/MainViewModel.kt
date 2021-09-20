package com.example.todo.presentation.main

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.CreateTodoSealed
import com.example.todo.ListTodoSealed
import com.example.todo.RequestSealed
import com.example.todo.domain.TodoService
import com.example.todo.model.Todo
import com.example.todo.model.TodoCache
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
        viewModelScope.launch(Dispatchers.IO) {
            when (val request = todoService.getTodoList()) {
                is RequestSealed.OnSuccess<*> -> {
                    mTodoCacheLiveData.postValue(ListTodoSealed.OnSuccess(request.data as List<TodoCache>))
                }
                is RequestSealed.OnFailure -> {
                    mTodoCacheLiveData.postValue(ListTodoSealed.OnFailure(request.err))
                }
            }
        }
    }

    fun getTodoById(id: Int) {
        mTodoState.value = CreateTodoSealed.OnProgressGet
        TODO(
            "Ketika object todo berhasil didapatkan," +
                    "lakukan assign value ke masing-masing liveData yang disediakan"
        )
    }

    private fun saveTodo(todo: Todo) {
        mTodoState.value = CreateTodoSealed.OnProgressSave
        TODO(
            "Lakukan penyimpanan todo dan ketika perhasil melakukan penyimpanan," +
                    "perbaharui data pada list"
        )
    }

    fun getCalender(view: View) {
        TODO(
            "Panggil DatePickerDialog untuk medapatkan tahun, bulan, dan tanggal." +
                    "kemudian lakukan pemanggilan method getHours()"
        )
    }

    private fun getHours(context: Context, calendar: Calendar) {
        TODO(
            "Panggil TimePickerDialog untuk medapatkan jam dan menit." +
                    "Kemudian update tampilan."
        )

    }

    fun submit(todo: Todo) {
        saveTodo(todo)
    }
}