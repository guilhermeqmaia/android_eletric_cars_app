package com.example.eletric_cars_app.data.local

import android.provider.BaseColumns

object CarsContract {

    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POWER = "power"
        const val COLUMN_NAME_RECHARGE = "recharge"
        const val COLUMN_NAME_PHOTO_URL = "photoUrl"
        const val COLUMN_NAME_CAR_ID = "carId"
    }

    const val CREATE_TABLE_CAR = """CREATE TABLE ${CarEntry.TABLE_NAME} (
        ${BaseColumns._ID} INTEGER PRIMARY KEY,
        ${CarEntry.COLUMN_NAME_PRICE} TEXT,
        ${CarEntry.COLUMN_NAME_BATTERY} TEXT,
        ${CarEntry.COLUMN_NAME_POWER} TEXT,
        ${CarEntry.COLUMN_NAME_RECHARGE} TEXT,
        ${CarEntry.COLUMN_NAME_PHOTO_URL} TEXT,
        ${CarEntry.COLUMN_NAME_CAR_ID} TEXT
    )
    """

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"

}