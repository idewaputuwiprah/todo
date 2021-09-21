package com.example.todo.presentation.main.fragment.date

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var listener: DatePickerListener? = null

    fun setListener(listener: DatePickerListener) {
        this.listener = listener
    }

    override fun onDetach() {
        super.onDetach()
        if (listener != null) listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity as Context, this, year, month, dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener?.onDialogDateSet(year, month, dayOfMonth)
    }
}

interface DatePickerListener{
    fun onDialogDateSet(year: Int, month: Int, dayOfMonth: Int)
}