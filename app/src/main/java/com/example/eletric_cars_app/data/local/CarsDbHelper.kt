package com.example.eletric_cars_app.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.eletric_cars_app.data.local.CarsContract.CREATE_TABLE_CAR
import com.example.eletric_cars_app.data.local.CarsContract.SQL_DELETE_ENTRIES

class CarsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CAR)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
        super.onDowngrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_NAME = "DbCar.db"
        const val DATABASE_VERSION = 1
    }
}