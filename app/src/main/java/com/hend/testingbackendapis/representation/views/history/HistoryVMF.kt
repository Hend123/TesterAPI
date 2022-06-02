package com.hend.testingbackendapis.representation.views.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hend.testingbackendapis.data.local.DBAdapter
import java.util.concurrent.ExecutorService


@Suppress("UNCHECKED_CAST")
class HistoryVMF(
    private val dbAdapter: DBAdapter, private val executorService: ExecutorService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryVM::class.java))
            return HistoryVM(dbAdapter, executorService) as T
        throw IllegalArgumentException("Unknown class name")
    }
}