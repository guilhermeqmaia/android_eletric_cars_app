package com.example.eletric_cars_app.presenter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.presenter.adapter.CarAdapter

class MainActivity : AppCompatActivity() {
    lateinit var buttonCalculate : Button
    lateinit var carsList : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupListeners()
        setupListViewData()
    }

    private fun setupListeners() {
        onTapCalculateButton()
    }

    private fun setupViews() {
        buttonCalculate = findViewById(R.id.btn_redirect)
        carsList = findViewById(R.id.rv_cars_list)
    }

    private fun setupListViewData() {
        val data = arrayOf("Cupcake", "Donut", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean")
        val carAdapter = CarAdapter(data)
        carsList.adapter = carAdapter

    }

    private fun onTapCalculateButton() {
        buttonCalculate.setOnClickListener {
            startActivity(Intent(this, CalculateAutonomyActivity::class.java))
        }
    }
}