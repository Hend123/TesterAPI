package com.hend.testingbackendapis.common.extensions

import java.net.HttpURLConnection

fun HashMap<String, MutableList<String>>.setHeaders(con: HttpURLConnection) {
    for (header in this) {
        if (header.value.size == 1) {
            con.setRequestProperty(header.key, header.value[0])
        } else {
            for (index in header.value.indices) {
                con.setRequestProperty(header.key, header.value[index])
            }
        }

    }
}