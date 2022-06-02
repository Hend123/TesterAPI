package com.hend.testingbackendapis.common.extensions

import android.util.Log
import java.net.HttpURLConnection

fun HttpURLConnection.getHeaders(): String {
    val headersMap: Map<String, List<String>> = this.getHeaderFields()
    val responseHeaders = StringBuffer()
    for (entry in headersMap.entries) {
        responseHeaders.appendLine("${entry.key} : ${entry.value}")
    }
    Log.i("test", "responseHeaders: $responseHeaders")
    return responseHeaders.toString()
}