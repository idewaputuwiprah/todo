package com.example.todo.utils

import android.app.Dialog
import android.content.Context
import android.nfc.FormatException
import android.view.LayoutInflater
import com.example.todo.LatLngException
import com.example.todo.R
import com.example.todo.StringEmptyException
import com.example.todo.databinding.ErrorViewBinding

class DialogUtils {
    companion object {
        fun alertDialogError(context: Context, e: Throwable) {
            val mBinding = ErrorViewBinding.inflate(LayoutInflater.from(context))
            val dialog = Dialog(context)
            dialog.apply {
                setContentView(mBinding.root)
                mBinding.apply {
                    tvErrorMessage.text = when (e) {
                        is StringEmptyException -> context.resources.getString(R.string.title_cannot_empty)
                        is LatLngException -> context.resources.getString(R.string.lat_lng_cannot_zero)
                        is FormatException -> context.resources.getString(R.string.format_date_is_not_correct)
                        else -> context.resources.getString(R.string.unknown)
                    }
                    btnClose.setOnClickListener { dialog.dismiss() }
                    show()
                }
            }
        }
    }
}