package com.hend.testingbackendapis.data.remote

import com.hend.testingbackendapis.model.ResponseAndRequest
import java.net.URL

interface IRequestHandler {
    fun requestPOST(
        url: URL,
        bodyRequest: String,
        requestHeaders: HashMap<String, MutableList<String>>
    ): ResponseAndRequest

    fun requestGET(url: URL, requestHeaders: HashMap<String, MutableList<String>>): ResponseAndRequest
}