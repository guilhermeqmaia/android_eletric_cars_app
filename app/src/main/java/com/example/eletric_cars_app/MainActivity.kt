package com.example.eletric_cars_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    lateinit var priceEditText: EditText
    lateinit var travelledKmEditText: EditText
    lateinit var buttonCalculate : Button
    lateinit var calculateResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupListeners()
    }

    fun setupListeners() {
        onTapCalculateButton()
    }

    fun setupViews() {
        priceEditText = findViewById(R.id.et_price_kwh)
        buttonCalculate = findViewById(R.id.btn_calculate)
        travelledKmEditText = findViewById(R.id.et_km_percorrido)
        calculateResult = findViewById(R.id.tv_calculate_result)
        calculateResult.visibility = View.INVISIBLE
    }

    private fun calculate() : Double? {
        return try {
            val price: String = priceEditText.text.toString()
            val travelledKm = travelledKmEditText.text.toString()
            price.toDouble() * travelledKm.toDouble()
        } catch (exception: NumberFormatException) {
            null
        }
    }

    private fun onTapCalculateButton() {
        buttonCalculate.setOnClickListener {
            val result = calculate()
            if (result != null) {
                calculateResult.text = "Resultado é: R$ ${DecimalFormat("#.##").format(result)}"
            } else {
                calculateResult.text = "Ocorreu um erro ao realizar o cálculo, verifique se os dados estão corretos"
            }
            calculateResult.visibility = View.VISIBLE
        }
    }
}