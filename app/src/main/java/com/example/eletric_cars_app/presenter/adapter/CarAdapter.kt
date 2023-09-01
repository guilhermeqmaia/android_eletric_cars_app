package com.example.eletric_cars_app.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.domain.Car

class CarAdapter(private val cars : List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.priceTextView.text = car.price
        holder.batteryTextView.text = car.battery
        holder.powerTextView.text = car.power
        holder.rechargeTextView.text = car.recharge
    }

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val priceTextView : TextView
        val batteryTextView : TextView
        val powerTextView : TextView
        val rechargeTextView : TextView

        init {
            view.apply {
                priceTextView = findViewById(R.id.tv_priceValue)
                batteryTextView = findViewById(R.id.tv_batteryValue)
                powerTextView = findViewById(R.id.tv_powerValue)
                rechargeTextView = findViewById(R.id.tv_rechargeValue)
            }
        }
    }
}

