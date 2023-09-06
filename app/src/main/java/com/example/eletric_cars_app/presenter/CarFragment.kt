package com.example.eletric_cars_app.presenter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.data.CarFactory
import com.example.eletric_cars_app.presenter.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarFragment : Fragment() {

    lateinit var fabCalculate : FloatingActionButton
    lateinit var carsList : RecyclerView
    val carFactory = CarFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupListViewData()
        onTapCalculateButton()
    }

    private fun setupView(view: View) {
        fabCalculate = view.findViewById(R.id.fab_calculate)
        carsList = view.findViewById(R.id.rv_cars_list)
    }

    private fun setupListViewData() {
        val data = carFactory.list
        val carAdapter = CarAdapter(data)
        carsList.adapter = carAdapter
    }

    private fun onTapCalculateButton() {
        fabCalculate.setOnClickListener {
           startActivity(Intent(context, CalculateAutonomyActivity::class.java))
        }
    }
}