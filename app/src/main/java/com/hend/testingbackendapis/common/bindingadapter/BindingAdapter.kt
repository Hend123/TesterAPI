package com.hend.testingbackendapis.common.bindingadapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@BindingAdapter("convertTimeStampToDateTime")
fun convertTimeStampToDateTime(textView: TextView, timeStamp: String): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
    val date = Date()
    date.time = timeStamp.toLong()  * 1000
    textView.text = dateFormat.format(date)
    return dateFormat.format(date)
}