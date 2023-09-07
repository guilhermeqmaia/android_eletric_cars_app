package com.example.eletric_cars_app.data

import com.example.eletric_cars_app.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsAPI {

    @GET("cars.json")
    fun getAllCars() : Call<List<Car>>
}