package com.hend.testingbackendapis.common

import com.hend.testingbackendapis.model.ResponseAndRequest

class Constants {
    companion object {
        const val GET: String = "GET"
        const val POST: String = "POST"
        const val DATABASE_NAME = "RESPONSES_AND_REQUESTS"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "rr_table"
        const val ID_COL = "id"
        const val URL_COl = "url"
        const val METHOD_TYPE_COL = "methodType"
        const val REQUEST_CODE_COL = "requestCode"
        const val ERROR_COL = "error"
        const val REQUEST_HEADERS_COL = "requestHeaders"
        const val RESPONSE_HEADERS_COL = "responseHeaders"
        const val REQUEST_BODY_COL = "requestBody"
        const val QUERY_PARAMS_COL = "queryParams"
        const val RESPONSE_COL = "responseBody"
        const val TIME_COL = "time"
        const val Response_And_Request = "responseAndRequest"
    }
}