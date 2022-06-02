package com.hend.testingbackendapis.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log

object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        Log.i("test","2")
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities: NetworkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)!!
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }

        } else {
            try {
                val activeNetworkInfo: NetworkInfo = connectivityManager.activeNetworkInfo!!
                if (activeNetworkInfo.isConnected) {
                    Log.i("test", "Network is available : true");
                    return true
                }
            } catch (e: Exception) {
                Log.i("test", "Network" + e.message)
            }
        }

        Log.i("test", "Network is available : FALSE ");
        return false;
    }
}