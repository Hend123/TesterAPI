package com.hend.testingbackendapis.data.local.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hend.testingbackendapis.common.Resource
import com.hend.testingbackendapis.data.local.DBAdapter
import com.hend.testingbackendapis.model.ResponseAndRequest
import java.util.concurrent.ExecutorService

class DBRepo(
    private val dbAdapter: DBAdapter, private val executorService: ExecutorService
) : IDBRepo {
    var responseAndRequests: MutableLiveData<Resource<MutableList<ResponseAndRequest>>> = MutableLiveData()


    override fun addResponseAndRequest(ResponseAndRequest: ResponseAndRequest) {
        executorService.execute {
            dbAdapter.addResponseAndRequest(ResponseAndRequest)
        }
    }

    override fun getResponseAndRequests(){
        var ResponseAndRequest: MutableList<ResponseAndRequest>?
        executorService.execute {
            this.responseAndRequests.postValue(Resource.loading())
            try {
                ResponseAndRequest = dbAdapter.getResponseAndRequests()
                this.responseAndRequests.postValue(Resource.success(ResponseAndRequest))
                Log.i("test", "1 this.responseAndRequests = ${this.responseAndRequests.value}")
            } catch (e: Exception) {
                this.responseAndRequests.postValue(Resource.error(e.toString()))
            }
        }


    }
}