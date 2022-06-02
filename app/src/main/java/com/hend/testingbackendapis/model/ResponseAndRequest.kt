package com.hend.testingbackendapis.model

import android.os.Parcel
import android.os.Parcelable
import com.hend.testingbackendapis.common.Constants.Companion.GET

data class ResponseAndRequest(
    val url: String = "",
    val methodType: String = GET,
    val requestCode: String = "Nothing",
    val response: String = "Nothing",
    val error: String = "Nothing",
    val requestHeaders:String = "Nothing",
    val responseHeaders:String = "Nothing",
    val requestBody: String = "Nothing",
    val queryParams: String = "Nothing",
    val time: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(methodType)
        parcel.writeString(requestCode)
        parcel.writeString(response)
        parcel.writeString(error)
        parcel.writeString(requestHeaders)
        parcel.writeString(responseHeaders)
        parcel.writeString(requestBody)
        parcel.writeString(queryParams)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseAndRequest> {
        override fun createFromParcel(parcel: Parcel): ResponseAndRequest {
            return ResponseAndRequest(parcel)
        }

        override fun newArray(size: Int): Array<ResponseAndRequest?> {
            return arrayOfNulls(size)
        }
    }
}

