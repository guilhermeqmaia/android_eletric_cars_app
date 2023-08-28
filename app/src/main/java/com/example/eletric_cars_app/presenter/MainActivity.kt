package com.example.eletric_cars_app.presenter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.eletric_cars_app.R

class MainActivity : AppCompatActivity() {
    lateinit var buttonCalculate : Button
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
        buttonCalculate = findViewById(R.id.btn_redirect)
    }

    private fun onTapCalculateButton() {
        buttonCalculate.setOnClickListener {
            startActivity(Intent(this, CalculateAutonomyActivity::class.java))
        }
    }
}