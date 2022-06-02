package com.hend.testingbackendapis.data.local.repository
import com.hend.testingbackendapis.model.ResponseAndRequest

interface IDBRepo {
    fun addResponseAndRequest(ResponseAndRequest: ResponseAndRequest)
    fun getResponseAndRequests()
}