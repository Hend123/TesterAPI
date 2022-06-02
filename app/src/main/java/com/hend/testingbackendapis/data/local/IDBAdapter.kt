package com.hend.testingbackendapis.data.local
import com.hend.testingbackendapis.model.ResponseAndRequest

interface IDBAdapter {
    fun addResponseAndRequest(ResponseAndRequest: ResponseAndRequest)
    fun getResponseAndRequests(): MutableList<ResponseAndRequest>?
}