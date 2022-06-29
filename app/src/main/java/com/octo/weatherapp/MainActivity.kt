package com.octo.weatherapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.data.NetworkModule
import com.example.presentation.WeatherWeeklyForecastDisplayModel
import com.example.presentation.WeatherWeeklyForecastView
import com.octo.weatherapp.databinding.ActivityMainBinding
import com.octo.weatherapp.mvvm.WeatherWeeklyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel : WeatherWeeklyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.viewFlipper.displayedChild = 1
                query?.let {
                    lifecycleScope.launch(Dispatchers.Default) {
                        viewModel.loadForecast(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        viewModel.successLiveData.observe(this){
            displayForecasts(it)
        }

        viewModel.failedLiveData.observe(this){ isFailed ->
            if(isFailed){
                displayError()
            }
        }

    }

    private fun displayForecasts(displayModel: WeatherWeeklyForecastDisplayModel) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.bestTemperatureTextView.text = displayModel.temperatureMax
            binding.worstTemperatureTextView.text = displayModel.temperatureMin
            binding.viewFlipper.displayedChild = 2
        }
    }

    private fun displayError() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.messageTextView.setText(R.string.unknown_city)
            binding.viewFlipper.displayedChild = 0
        }
    }
}