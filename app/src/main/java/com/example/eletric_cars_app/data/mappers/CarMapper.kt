package com.example.eletric_cars_app.data.mappers

import com.example.eletric_cars_app.domain.Car
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CarMapper : JsonDeserializer<Car> {
    override fun deserialize(
        json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?
    ): Car {
        val response = json?.asJsonObject
        val id = response?.get("id")?.asInt ?: 0
        val price = response?.get("preco")?.asString ?: ""
        val battery = response?.get("bateria")?.asString ?: ""
        val power = response?.get("potencia")?.asString ?: ""
        val recharge = response?.get("recarga")?.asString ?: ""
        val urlPhoto = response?.get("urlPhoto")?.asString ?: ""

        return Car(
            null, price, battery, power, recharge, urlPhoto, id.toString(),
        )
    }
}