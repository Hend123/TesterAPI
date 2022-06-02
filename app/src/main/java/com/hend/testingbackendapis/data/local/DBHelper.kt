package com.hend.testingbackendapis.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hend.testingbackendapis.common.Constants.Companion.DATABASE_NAME
import com.hend.testingbackendapis.common.Constants.Companion.DATABASE_VERSION
import com.hend.testingbackendapis.common.Constants.Companion.ERROR_COL
import com.hend.testingbackendapis.common.Constants.Companion.ID_COL
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


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                URL_COl + " TEXT," +
                METHOD_TYPE_COL + " TEXT," +
                REQUEST_CODE_COL + " TEXT," +
                ERROR_COL + " TEXT," +
                REQUEST_HEADERS_COL + " TEXT," +
                RESPONSE_HEADERS_COL + " TEXT," +
                REQUEST_BODY_COL + " TEXT," +
                QUERY_PARAMS_COL + " TEXT," +
                RESPONSE_COL + " TEXT," +
                TIME_COL + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}
