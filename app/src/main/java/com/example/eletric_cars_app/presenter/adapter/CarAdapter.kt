package com.example.eletric_cars_app.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.domain.Car

class CarAdapter(private val cars : List<Car>, private val isFavoritesFragment : Boolean = false) : RecyclerView.Adapter<CarAdapter.CarViewHolder>(){

    var carItemListener: (Car) -> Unit = {}

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
        if(isFavoritesFragment) {
            holder.favoriteImageView.setImageResource(R.drawable.star_filled)
        }
        holder.favoriteImageView.setOnClickListener {
            carItemListener(car)
            setupFavorite(car, holder)
        }
    }

    private fun setupFavorite(
        car: Car,
        holder: CarViewHolder
    ) {
        car.isFavorite = !car.isFavorite

        if (car.isFavorite) {
            holder.favoriteImageView.setImageResource(R.drawable.star_filled)
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.star_empty)
        }
    }

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val priceTextView : TextView
        val batteryTextView : TextView
        val powerTextView : TextView
        val rechargeTextView : TextView
        val favoriteImageView : ImageView

        init {
            view.apply {
                priceTextView = findViewById(R.id.tv_priceValue)
                batteryTextView = findViewById(R.id.tv_batteryValue)
                powerTextView = findViewById(R.id.tv_powerValue)
                rechargeTextView = findViewById(R.id.tv_rechargeValue)
                favoriteImageView = findViewById(R.id.iv_favorite)
            }
        }
    }
}

