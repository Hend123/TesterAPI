package com.hend.testingbackendapis.data.remote.repository

import java.net.URL

interface IRequestHandlerRepo {
    fun requestPOST(
        url: URL,
        bodyRequest: String,
        requestHeaders: HashMap<String, MutableList<String>>
    )

    fun requestGET(url: URL, requestHeaders: HashMap<String, MutableList<String>>)
}