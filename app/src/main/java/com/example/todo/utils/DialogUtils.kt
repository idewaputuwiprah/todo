package com.example.todo.utils

import android.content.Context
import android.nfc.FormatException
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.todo.LatLngException
import com.example.todo.R
import com.example.todo.StringEmptyException

class DialogUtils {
    companion object {
        fun alertDialogError(context: Context, e: Throwable) {
            TODO(
                "Muncul sebuah dialog dengan ketentuan dibawah ini, " +
                        "gunakan R.layout_error_view untuk dialog view" +
                        "StringEmptyException sebagai R.string.title_cannot_empty," +
                        "LatLngException sebagai R.string.lat_lng_cannot_zero," +
                        "FormatException sebagai R.string.format_date_is_not_correct" +
                        "else sebagai R.string.unknown"
            )
        }
    }
}