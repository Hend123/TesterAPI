package com.hend.testingbackendapis.data.local

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import com.hend.testingbackendapis.common.Constants.Companion.ERROR_COL
import com.hend.testingbackendapis.common.Constants.Companion.METHOD_TYPE_COL
import com.hend.testingbackendapis.common.Constants.Companion.QUERY_PARAMS_COL
import com.hend.testingbackendapis.common.Constants.Companion.REQUEST_BODY_COL
import com.hend.testingbackendapis.common.Constants.Companion.REQUEST_CODE_COL
import com.hend.testingbackendapis.common.Constants.Companion.REQUEST_HEADERS_COL
import com.hend.testingbackendapis.common.Constants.Companion.RESPONSE_COL
import com.hend.testingbackendapis.common.Constants.Companion.RESPONSE_HEADERS_COL
import com.hend.testingbackendapis.common.Constants.Companion.TABLE_NAME
import com.hend.testingbackendapis.common.Constants.Companion.TIME_COL
import com.hend.testingbackendapis.common.Constants.Companion.URL_COl
import com.hend.testingbackendapis.model.ResponseAndRequest

class DBAdapter(val dbHelper: DBHelper) : IDBAdapter {


    override fun addResponseAndRequest(ResponseAndRequest: ResponseAndRequest) {
        val values = ContentValues()
        values.put(URL_COl, ResponseAndRequest.url)
        values.put(METHOD_TYPE_COL, ResponseAndRequest.methodType)
        values.put(REQUEST_CODE_COL, ResponseAndRequest.requestCode)
        values.put(ERROR_COL, ResponseAndRequest.error)
        values.put(REQUEST_HEADERS_COL, ResponseAndRequest.requestHeaders)
        values.put(RESPONSE_HEADERS_COL, ResponseAndRequest.responseHeaders)
        values.put(REQUEST_BODY_COL, ResponseAndRequest.requestBody)
        values.put(QUERY_PARAMS_COL, ResponseAndRequest.queryParams)
        values.put(RESPONSE_COL, ResponseAndRequest.response)
        values.put(TIME_COL, ResponseAndRequest.time)
        val db = dbHelper.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun getResponseAndRequests(): MutableList<ResponseAndRequest>? {
        val db = dbHelper.readableDatabase
        val responseAndRequestList: MutableList<ResponseAndRequest> = mutableListOf()
        val c: Cursor
        val columns: Array<String> = arrayOf(
            URL_COl, METHOD_TYPE_COL, REQUEST_CODE_COL, ERROR_COL,
            REQUEST_HEADERS_COL, RESPONSE_HEADERS_COL, REQUEST_BODY_COL, QUERY_PARAMS_COL,RESPONSE_COL, TIME_COL
        )
        c = db.query(TABLE_NAME, columns, null, null, null, null, null)
        c.use { c ->
            while (c.moveToNext()) {
                responseAndRequestList!!.add(
                    ResponseAndRequest(
                        url = c.getString(0), methodType = c.getString(1),
                        requestCode = c.getString(2), error = c.getString(3),
                        requestHeaders = c.getString(4), responseHeaders = c.getString(5),
                        requestBody = c.getString(6), queryParams = c.getString(7),
                        response = c.getString(8),
                        time = c.getString(9)
                    )
                )
            }
        }
        Log.i("test", "1 outPutList = $responseAndRequestList")
        return responseAndRequestList
    }


}
