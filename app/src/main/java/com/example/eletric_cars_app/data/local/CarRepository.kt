package com.example.eletric_cars_app.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_BATTERY
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_PHOTO_URL
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_POWER
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_PRICE
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_RECHARGE
import com.example.eletric_cars_app.domain.Car

class CarRepository(private val context: Context) {

    private fun save(car: Car): Long? {
        try {

            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME_PRICE, car.price)
                put(COLUMN_NAME_BATTERY, car.battery)
                put(COLUMN_NAME_POWER, car.power)
                put(COLUMN_NAME_RECHARGE, car.recharge)
                put(COLUMN_NAME_PHOTO_URL, car.photoUrl)
                put(COLUMN_NAME_CAR_ID, car.carId)
            }

            return db?.insert(CarsContract.CarEntry.TABLE_NAME, null, values)
        } catch (error: Exception) {
            error.message?.let { Log.e("Erro ao inserir os dados", it) }
        }
        return null
    }

    fun findCarById(carId: String): Car? {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_PHOTO_URL,
            COLUMN_NAME_CAR_ID,
        )

        val filter = "${COLUMN_NAME_CAR_ID} = ?"

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME, columns, filter, arrayOf("$carId"), null, null, null
        )
        val cars = mutableListOf<Car>()
        with(cursor) {
            while (moveToNext()) {
                cars.add(
                    Car(
                        id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                        price = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE)),
                        battery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY)),
                        power = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER)),
                        recharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE)),
                        photoUrl = getString(getColumnIndexOrThrow(COLUMN_NAME_PHOTO_URL)),
                        carId = getString(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)),
                        isFavorite = true,
                    )
                )
            }
        }
        cursor.close()
        if (cars.size == 0) return null
        return cars.first()
    }

    fun saveIfNotExist(car: Car): Long? {
        try {
            findCarById(car.carId)
        } catch (e: Exception) {
            null
        } ?: return save(car)
        return null
    }

    fun getAllCars(): List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_PHOTO_URL,
            COLUMN_NAME_CAR_ID,
        )

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME, columns, null, null, null, null, null
        )
        val cars = mutableListOf<Car>()
        with(cursor) {
            while (moveToNext()) {
                cars.add(
                    Car(
                        id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                        price = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE)),
                        battery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY)),
                        power = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER)),
                        recharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE)),
                        photoUrl = getString(getColumnIndexOrThrow(COLUMN_NAME_PHOTO_URL)),
                        carId = getString(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)),
                        isFavorite = true,
                    )
                )
            }
        }
        cursor.close()
        return cars
    }
}