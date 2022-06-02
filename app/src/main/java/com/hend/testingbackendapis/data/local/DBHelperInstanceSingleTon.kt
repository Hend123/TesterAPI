package com.hend.testingbackendapis.data.local

import android.content.Context

object DBHelperInstanceSingleTon {
    fun newsInstance(context: Context): DBHelper {
        return DBHelper(context)
    }

}
