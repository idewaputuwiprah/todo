package com.example.todo.presentation.main.fragment.date

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var listener: TimePickerListener? = null

    fun setListener(listener: TimePickerListener) {
        this.listener = listener
    }

    override fun onDetach() {
        super.onDetach()
        if (listener != null) listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        listener?.onDialogTimeSet(hourOfDay, minute)
    }
}

interface TimePickerListener {
    fun onDialogTimeSet(hourOfDay: Int, minute: Int)
}