package com.example.todo.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        const val FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:SSS'Z'"
    }

    fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        return getDateWithFormatFromTimeMilis(calender.timeInMillis)
    }

    fun checkFormatDate(date: String): Boolean {
        return try {
            val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            simpleDateFormat.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getMilisByDate(date: String): Long {
        val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        return simpleDateFormat.parse(date).time
    }

    fun getDateWithFormatFromTimeMilis(milis: Long): String {
        val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        return simpleDateFormat.format(milis)
    }
}