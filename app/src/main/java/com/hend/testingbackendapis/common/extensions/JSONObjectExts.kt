package com.hend.testingbackendapis.common.extensions

import org.json.JSONObject
import java.net.URLEncoder

fun JSONObject.encodeRequestBody(): String {
    val result = StringBuilder()
    var first = true
    val itr = this.keys()
    while (itr.hasNext()) {
        val key = itr.next()
        val value = this[key]
        if (first) first = false else result.append("&")
        result.append(URLEncoder.encode(key, "UTF-8"))
        result.append("=")
        result.append(URLEncoder.encode(value.toString(), "UTF-8"))

    }
    return result.toString()
}