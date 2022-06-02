package com.hend.testingbackendapis.representation.views.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hend.testingbackendapis.common.Resource
import com.hend.testingbackendapis.data.local.DBAdapter
import com.hend.testingbackendapis.data.local.repository.DBRepo
import com.hend.testingbackendapis.model.ResponseAndRequest
import java.util.concurrent.ExecutorService

interface IHistoryVM {
    fun getResponseAndRequests(): MutableLiveData<Resource<MutableList<ResponseAndRequest>>>
}

class HistoryVM(
    dbAdapter: DBAdapter, executorService: ExecutorService
): ViewModel(), IHistoryVM {
    private val dbRepo = DBRepo(dbAdapter, executorService)
    var historys:MutableLiveData<Resource<MutableList<ResponseAndRequest>>> = MutableLiveData()
    override fun getResponseAndRequests():MutableLiveData<Resource<MutableList<ResponseAndRequest>>> {
        dbRepo.getResponseAndRequests()
        Log.i("test", "1 dbRepo.responseAndRequests = ${dbRepo.responseAndRequests.value}")
        historys.postValue(dbRepo.responseAndRequests.value)
        return dbRepo.responseAndRequests
    }
}