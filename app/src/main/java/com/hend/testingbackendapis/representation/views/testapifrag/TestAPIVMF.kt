package com.hend.testingbackendapis.representation.views.testapifrag

import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hend.testingbackendapis.data.local.DBAdapter
import java.util.concurrent.ExecutorService


@Suppress("UNCHECKED_CAST")
class TestAPIVMF(
    private val dbAdapter: DBAdapter, private val executorService: ExecutorService,
    private val handler: Handler
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestAPIVM::class.java))
            return TestAPIVM(dbAdapter, executorService, handler) as T
        throw IllegalArgumentException("Unknown class name")
    }
}