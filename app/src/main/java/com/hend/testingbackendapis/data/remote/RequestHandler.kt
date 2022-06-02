package com.hend.testingbackendapis.data.remote

import android.util.Log
import com.hend.testingbackendapis.common.Constants.Companion.GET
import com.hend.testingbackendapis.common.Constants.Companion.POST
import com.hend.testingbackendapis.common.extensions.encodeRequestBody
import com.hend.testingbackendapis.common.extensions.getHeaders
import com.hend.testingbackendapis.common.extensions.getQueryParams
import com.hend.testingbackendapis.common.extensions.setHeaders
import com.hend.testingbackendapis.model.ResponseAndRequest
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

object RequestHandler : IRequestHandler {
    override fun requestPOST(
        url: URL,
        bodyRequest: String,
        requestHeaders: HashMap<String, MutableList<String>>
    ): ResponseAndRequest {
        Log.i("test", "requestHeaderss = $requestHeaders")
        var ResponseAndRequest: ResponseAndRequest
        var conn: HttpURLConnection? = null
        try {
            conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 3000
            conn.connectTimeout = 3000
            conn.requestMethod = POST
            conn.doInput = true
            conn.doOutput = true
            requestHeaders.setHeaders(conn)
            val os: OutputStream = conn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            val requestBody = JSONObject(bodyRequest).encodeRequestBody()
            writer.write(requestBody)
            writer.flush()
            writer.close()
            os.close()
            val responseCode: Int = conn.responseCode
            // To Check for 200
            Log.i("test", responseCode.toString())
            if (responseCode in 100..399) {
                val response = conn.inputStream.bufferedReader().use(BufferedReader::readText)
                Log.i("test", "response = $response")

                ResponseAndRequest = ResponseAndRequest(
                    url = url.toString(),
                    methodType = POST,
                    response = response,
                    requestCode = responseCode.toString(),
                    responseHeaders = conn.getHeaders(),
                    requestBody = requestBody,
                    requestHeaders = requestHeaders.toString(),
                    time = (System.currentTimeMillis()/1000).toString()
                )
            } else {
                val response = conn.errorStream.bufferedReader().use(BufferedReader::readText)
                Log.i("test", "response  = $response")
                ResponseAndRequest = ResponseAndRequest(
                    url = url.toString(),
                    methodType = POST,
                    response = response,
                    requestCode = responseCode.toString(),
                    responseHeaders = conn.getHeaders(),
                    requestBody = requestBody,
                    requestHeaders = requestHeaders.toString(),
                    time = (System.currentTimeMillis()/1000).toString()
                )
            }
        } catch (e: Exception) {
            Log.i("test", "error $e")
            ResponseAndRequest = ResponseAndRequest(
                url = url.toString(),
                error = e.message.toString(),
                time = (System.currentTimeMillis()/1000).toString()
            )
        } finally {
            conn?.disconnect()
        }
        return ResponseAndRequest
    }


    override fun requestGET(url: URL, requestHeaders: HashMap<String, MutableList<String>>): ResponseAndRequest {
        var ResponseAndRequest: ResponseAndRequest
        var con: HttpURLConnection? = null
        try {
            con = url.openConnection() as HttpURLConnection
            con.requestMethod = GET
            requestHeaders.setHeaders(con)
            val responseCode = con.responseCode
            Log.i("test", responseCode.toString())
            if (responseCode in 100..399) { // connection ok
                val response = con.inputStream.bufferedReader().use(BufferedReader::readText)
                ResponseAndRequest = ResponseAndRequest(
                    url = url.toString(),
                    response = response,
                    requestCode = responseCode.toString(),
                    responseHeaders = con.getHeaders(),
                    queryParams = url.toString().getQueryParams(),
                    requestHeaders = requestHeaders.toString(),
                    time = (System.currentTimeMillis()/1000).toString()
                )
            } else {
                val response = con.errorStream.bufferedReader().use(BufferedReader::readText)
                Log.i("test", "response  = $response")
                ResponseAndRequest = ResponseAndRequest(
                    url = url.toString(),
                    response = response,
                    requestCode = responseCode.toString(),
                    responseHeaders = con.getHeaders(),
                    queryParams = url.toString().getQueryParams(),
                    requestHeaders = requestHeaders.toString(),
                    time = (System.currentTimeMillis()/1000).toString()
                )
            }
        } catch (e: Exception) {
            Log.i("test", "Exception = $e")
            ResponseAndRequest = ResponseAndRequest(
                url = url.toString(),
                error = e.message.toString(),
                time = (System.currentTimeMillis()/1000).toString()
            )
        } finally {
            con?.disconnect()
        }
        return ResponseAndRequest
    }


}