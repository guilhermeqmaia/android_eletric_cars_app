package com.example.eletric_cars_app.presenter

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.opengl.Visibility
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletric_cars_app.R
import com.example.eletric_cars_app.data.CarFactory
import com.example.eletric_cars_app.domain.Car
import com.example.eletric_cars_app.presenter.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {

    lateinit var fabCalculate : FloatingActionButton
    lateinit var carsList : RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var emptyStateImage : ImageView
    lateinit var emptyStateText : TextView
    lateinit var emptyStateRetryButton: Button
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
        setupListViewData()
        onTapCalculateButton()
        execute(view.context)
    }

    private fun execute(context: Context) {
        val isConnected = isInternetOn(context)
        if(isConnected) {
            GetCarInformation().execute("https://igorbag.github.io/cars-api/cars.json")
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

    private fun setupListViewData() {
        val carAdapter = CarAdapter(carsArray)
        carsList.adapter = carAdapter
    }

    private fun onTapCalculateButton() {
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

    inner class GetCarInformation : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg url: String?): String {
            var urlConnection : HttpURLConnection? = null
            try {
                val urlBase = URL(url[0])
                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty("Accept", "application/json")

                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço indisponível no momento")
                }
            } catch (exception : Exception) {
                Log.e("Error on Do In Background", exception.message.toString())
            } finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    val price = jsonArray.getJSONObject(i).getString("preco")
                    val battery = jsonArray.getJSONObject(i).getString("bateria")
                    val power = jsonArray.getJSONObject(i).getString("potencia")
                    val recharge = jsonArray.getJSONObject(i).getString("recarga")
                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")


                    val model = Car(
                            id=id.toInt(),
                            price = price,
                            battery = battery,
                            power = power,
                            recharge = recharge,
                            photoUrl = urlPhoto,
                        )

                    carsArray.add(model)
                }


            } catch (ex: Exception) {
                Log.e("Error", ex.message.toString())
            } finally {
                setupListViewData()
                onSuccessState()
            }
        }
    }
}