package com.hend.testingbackendapis.common.extensions

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun String.getQueryParams(): String {
    val uri: Uri =
        Uri.parse(this)
    val args: Set<String> = uri.queryParameterNames!!
    Log.i("test", "args = $args")
    return args.toString()
}
@SuppressLint("SimpleDateFormat")
fun timestampToDateString(timestamp: Long): String? {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val date = Date(timestamp)
    return dateFormat.format(date)
}
