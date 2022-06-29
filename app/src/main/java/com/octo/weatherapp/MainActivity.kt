package com.octo.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.data.NetworkModule
import com.example.presentation.WeatherWeeklyForecastDisplayModel
import com.example.presentation.WeatherWeeklyForecastView
import com.octo.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), WeatherWeeklyForecastView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: WeatherWeeklyController
    private lateinit var module: ForecastModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        module = ForecastModule(this, NetworkModule())

        controller = module.provideController()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.viewFlipper.displayedChild = 1
                query?.let {
                    lifecycleScope.launch(Dispatchers.Default) {
                        controller.loadForecast(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = false

        })
    }

    override fun displayForecasts(displayModel: WeatherWeeklyForecastDisplayModel) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.bestTemperatureTextView.text = displayModel.temperatureMax
            binding.worstTemperatureTextView.text = displayModel.temperatureMin
            binding.viewFlipper.displayedChild = 2
        }
    }

    override fun displayError() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.messageTextView.setText(R.string.unknown_city)
            binding.viewFlipper.displayedChild = 0
        }
    }
}