package com.hend.testingbackendapis.data.remote.repository

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hend.testingbackendapis.common.Resource
import com.hend.testingbackendapis.data.local.DBAdapter
import com.hend.testingbackendapis.data.remote.RequestHandler
import com.hend.testingbackendapis.model.ResponseAndRequest
import java.net.URL
import java.util.concurrent.ExecutorService

class RequestHandlerRepo(
    private val dbAdapter: DBAdapter, private val executorService: ExecutorService,
    private val handler: Handler
) : IRequestHandlerRepo {
    var ResponseAndRequest: MutableLiveData<Resource<ResponseAndRequest>> = MutableLiveData()
    override fun requestPOST(
        url: URL,
        bodyRequest: String,
        requestHeaders: HashMap<String, MutableList<String>>
    ) {
        var ResponseAndRequest: ResponseAndRequest?
        executorService.execute {
            Log.i("test", "6 $RequestHandler.requestPOST(url, bodyRequest, requestHeaders)")
            this.ResponseAndRequest.postValue(Resource.loading())
            try {
                ResponseAndRequest = RequestHandler.requestPOST(url, bodyRequest, requestHeaders)
                this.ResponseAndRequest.postValue(Resource.success(ResponseAndRequest))
                dbAdapter.addResponseAndRequest(ResponseAndRequest!!)
            } catch (e: Exception) {
                this.ResponseAndRequest.postValue(Resource.error(e.toString()))
            }


        }
    }

    override fun requestGET(url: URL, requestHeaders: HashMap<String, MutableList<String>>) {
        var ResponseAndRequest: ResponseAndRequest?
        executorService.execute {
            this.ResponseAndRequest.postValue(Resource.loading())
            try {
                ResponseAndRequest = RequestHandler.requestGET(url, requestHeaders)
                this.ResponseAndRequest.postValue(Resource.success(ResponseAndRequest))
                dbAdapter.addResponseAndRequest(ResponseAndRequest!!)
            } catch (e: Exception) {
                this.ResponseAndRequest.postValue(Resource.error(e.toString()))
            }
        }
    }
}