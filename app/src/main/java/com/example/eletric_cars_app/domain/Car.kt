package com.example.eletric_cars_app.domain

data class Car (
    val id: Long?,
    val price: String,
    val battery: String,
    val power: String,
    val recharge: String,
    val photoUrl: String,
    val carId: String,
    var isFavorite: Boolean = false,
)