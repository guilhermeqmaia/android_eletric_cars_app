package com.example.eletric_cars_app.presenter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.data.CarsAPI
import com.example.eletric_cars_app.data.local.CarRepository
import com.example.eletric_cars_app.data.local.CarsContract
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_BATTERY
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_PHOTO_URL
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_POWER
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_PRICE
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.COLUMN_NAME_RECHARGE
import com.example.eletric_cars_app.data.local.CarsContract.CarEntry.TABLE_NAME
import com.example.eletric_cars_app.data.local.CarsDbHelper
import com.example.eletric_cars_app.data.mappers.CarMapper
import com.example.eletric_cars_app.domain.Car
import com.example.eletric_cars_app.presenter.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CarFragment : Fragment() {

    lateinit var fabCalculate : FloatingActionButton
    lateinit var carsList : RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var emptyStateImage : ImageView
    lateinit var emptyStateText : TextView
    lateinit var emptyStateRetryButton: Button
    lateinit var carsApi : CarsAPI
    var carsArray :  ArrayList<Car> = ArrayList()


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
        setupRetrofit()
        setupListeners()
        execute(view.context)
    }

    override fun onResume() {
        super.onResume()
        getAllCars()
    }

    private fun setupRetrofit() {
        val gson = Gson().newBuilder()
            .registerTypeAdapter(Car::class.java, CarMapper())
            .create()

        val builder = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        carsApi = builder.create()
    }

    private fun getAllCars() {
        carsApi.getAllCars().enqueue(object: Callback<List<Car>>{
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful) {
                    response.body()?.let {setupListViewData(it) }
                    onSuccessState()
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) = Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
        })
    }

    private fun execute(context: Context?) {
        val isConnected = isInternetOn(context)
        if(isConnected) {
            getAllCars()
            onExecute()
        } else onEmptyState()
    }

    private fun onExecute() {
        progressBar.visibility = View.VISIBLE
        emptyStateImage.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        emptyStateRetryButton.visibility = View.GONE
    }

    private fun onEmptyState() {
        progressBar.visibility = View.GONE
        carsList.visibility = View.GONE
        fabCalculate.visibility = View.GONE
        emptyStateImage.visibility = View.VISIBLE
        emptyStateText.visibility = View.VISIBLE
        emptyStateRetryButton.visibility = View.VISIBLE
        carsArray.clear()
    }

    private fun onSuccessState() {
        carsList.visibility = View.VISIBLE
        fabCalculate.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        emptyStateImage.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        emptyStateRetryButton.visibility = View.GONE
    }

    private fun setupView(view: View) {
        fabCalculate = view.findViewById(R.id.fab_calculate)
        carsList = view.findViewById(R.id.rv_cars_list)
        progressBar = view.findViewById(R.id.pb_loader)
        emptyStateImage = view.findViewById(R.id.iv_empty_state)
        emptyStateText = view.findViewById(R.id.tv_connection_off)
        emptyStateRetryButton = view.findViewById(R.id.btn_retry)
    }

    private fun setupListViewData(cars: List<Car>) {
        val carAdapter = CarAdapter(cars)
        carAdapter.carItemListener = {CarRepository(requireContext()).saveIfNotExist(it)}
        carsList.adapter = carAdapter
    }

    private fun setupListeners() {
        fabCalculate.setOnClickListener {
           startActivity(Intent(context, CalculateAutonomyActivity::class.java))
        }
        emptyStateRetryButton.setOnClickListener {
            context?.let { it1 -> execute(it1) }
        }
    }

    private fun isInternetOn(context: Context?) : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected()
        }
    }


}