package com.example.eletric_cars_app.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R

class CarAdapter(private val cars : Array<String>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.textView.text = car
    }

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView : TextView

        init {
            textView = view.findViewById(R.id.tv_priceValue)
        }
    }
}

