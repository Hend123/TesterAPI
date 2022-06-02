package com.hend.testingbackendapis.representation.views.testapifrag

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hend.testingbackendapis.common.Resource
import com.hend.testingbackendapis.data.local.DBAdapter
import com.hend.testingbackendapis.data.remote.repository.RequestHandlerRepo
import com.hend.testingbackendapis.model.ResponseAndRequest
import java.net.URL
import java.util.concurrent.ExecutorService

interface ITestAPIVM {
    fun requestPOST(
        url: URL,
        bodyRequest: String,
        requestHeaders: HashMap<String, MutableList<String>>
    ): MutableLiveData<Resource<ResponseAndRequest>>

    fun requestGET(url: URL, requestHeaders: HashMap<String, MutableList<String>>)
            : MutableLiveData<Resource<ResponseAndRequest>>
}

class TestAPIVM(
    dbAdapter: DBAdapter, executorService: ExecutorService,
    handler: Handler
) : ViewModel(), ITestAPIVM {
    private val requestHandlerRepo = RequestHandlerRepo(dbAdapter, executorService, handler)
    var requestAndRequest:MutableLiveData<Resource<ResponseAndRequest>> = MutableLiveData()

    override fun requestPOST(
        url: URL,
        bodyRequest: String,
        requestHeaders: HashMap<String, MutableList<String>>
    ): MutableLiveData<Resource<ResponseAndRequest>> {
        Log.i("test", "5")
        requestHandlerRepo.requestPOST(url, bodyRequest, requestHeaders)
        requestAndRequest.postValue(requestHandlerRepo.ResponseAndRequest.value)
        return requestHandlerRepo.ResponseAndRequest
    }

    override fun requestGET(url: URL, requestHeaders: HashMap<String, MutableList<String>>)
            : MutableLiveData<Resource<ResponseAndRequest>> {
        requestHandlerRepo.requestGET(url, requestHeaders)
        requestAndRequest.postValue(requestHandlerRepo.ResponseAndRequest.value)
        return requestHandlerRepo.ResponseAndRequest
    }
}