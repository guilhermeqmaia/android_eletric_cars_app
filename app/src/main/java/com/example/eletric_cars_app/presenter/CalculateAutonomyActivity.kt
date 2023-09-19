package com.example.eletric_cars_app.presenter

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eletric_cars_app.R

class CalculateAutonomyActivity : AppCompatActivity() {

    lateinit var price : EditText
    lateinit var runnedDistance : EditText
    lateinit var result : TextView
    lateinit var btnCalculate : Button
    lateinit var iconClose : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_autonomy)
        setupView()
        setupListeners()
        setupCachedResult()
    }

    private fun setupCachedResult() {
        val calcValue = getSharedPref()
        result.text = calcValue.toString()
    }

    private fun setupView() {
        price = findViewById(R.id.et_price_kwh)
        runnedDistance = findViewById(R.id.et_km_percorrido)
        result = findViewById(R.id.tv_result)
        btnCalculate = findViewById(R.id.btn_calculate)
        iconClose = findViewById(R.id.ic_close)
    }

    private fun setupListeners() {
        btnCalculate.setOnClickListener {
            calculate()
        }
        iconClose.setOnClickListener {
            onClose()
        }
    }

    private fun calculate() {
        val price = price.text.toString().toFloat()
        val km = runnedDistance.text.toString().toFloat()
        val value = price / km

        result.text = value.toString()
        saveSharedPref(value)
    }

    private fun onClose() {
        finish()
    }

    private fun saveSharedPref(result : Float) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()) {
            putFloat(getString(R.string.saved_calc), result)
            apply()
        }
    }

    private fun getSharedPref() : Float {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(getString(R.string.saved_calc), 0.0f)
    }
}