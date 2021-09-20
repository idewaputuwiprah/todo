package com.example.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.domain.TodoService
import com.example.todo.presentation.main.MainViewModel
import com.example.todo.utils.DateUtils
import javax.inject.Inject


class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Buatlah factory untuk membuat viewmodel jika dibutuhkan")
    }
}