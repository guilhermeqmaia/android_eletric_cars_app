package com.example.eletric_cars_app.presenter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eletric_cars_app.presenter.CarFragment
import com.example.eletric_cars_app.presenter.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CarFragment()
            1 -> FavoritesFragment()
            else -> FavoritesFragment()
        }
    }
}