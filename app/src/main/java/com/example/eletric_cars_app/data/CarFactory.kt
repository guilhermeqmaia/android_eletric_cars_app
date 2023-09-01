package com.example.eletric_cars_app.data

import com.example.eletric_cars_app.domain.Car

class CarFactory {
    val list = listOf(
        Car(
            id = 1,
            price = "R$ 300.000,00",
            battery = "300 kWh",
            power = "300cv",
            recharge = "50min",
            photoUrl = "www.google.com.br",
        ),
        Car(
            id = 2,
            price = "R$ 500.000,00",
            battery = "200 kWh",
            power = "400cv",
            recharge = "60min",
            photoUrl = "www.google.com.br",
        ),
        Car(
            id = 3,
            price = "R$ 1.000.000,00",
            battery = "500 kWh",
            power = "150cv",
            recharge = "30min",
            photoUrl = "www.google.com.br",
        ),
        Car(
            id = 4,
            price = "R$ 800.000,00",
            battery = "200 kWh",
            power = "300cv",
            recharge = "40min",
            photoUrl = "www.google.com.br",
        )
    )
}