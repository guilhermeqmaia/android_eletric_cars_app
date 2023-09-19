package com.example.eletric_cars_app.presenter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.data.local.CarRepository
import com.example.eletric_cars_app.presenter.adapter.CarAdapter

class FavoritesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListViewData(view)
    }

    override fun onResume() {
        super.onResume()
        setupListViewData(requireView())
    }

    private fun setupListViewData(view: View) {
        recyclerView = view.findViewById(R.id.rv_cars_list)
        val repository = CarRepository(requireContext())
        val cars = repository.getAllCars()
        val carAdapter = CarAdapter(cars, true)
        recyclerView.adapter = carAdapter
        recyclerView.visibility = View.VISIBLE
    }

}