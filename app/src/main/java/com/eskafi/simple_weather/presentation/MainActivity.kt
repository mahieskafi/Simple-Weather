package com.eskafi.simple_weather.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.eskafi.simple_weather.BuildConfig
import com.eskafi.simple_weather.R
import com.eskafi.simple_weather.databinding.ActivityMainBinding
import com.eskafi.simple_weather.domain.model.MainInfo
import com.eskafi.simple_weather.domain.model.TemperatureInfo
import com.eskafi.simple_weather.presentation.weather.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var adapter: ForecastAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ForecastAdapter()
        binding.forecastView.nextDaysRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.forecastView.nextDaysRecycler.adapter = adapter

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadData()
            subscribeCurrentWeatherResult()
            subscribeForecastResult()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    private fun subscribeCurrentWeatherResult() {
        lifecycleScope.launch {
            viewModel.currentWeatherState.observe(this@MainActivity) { weatherState ->
                when (weatherState) {
                    is WeatherState.IsLoading -> {
                        binding.loading.visibility = View.VISIBLE
                        binding.currentWeatherView.root.visibility = View.GONE
                        binding.forecastView.root.visibility = View.GONE
                        binding.currentWeatherErrorView.root.visibility = View.GONE
                    }
                    is WeatherState.ShowError -> {
                        binding.loading.visibility = View.GONE
                        binding.currentWeatherView.root.visibility = View.GONE
                        binding.currentWeatherErrorView.root.visibility = View.VISIBLE

                        binding.currentWeatherErrorView.errorTextView.text = weatherState.message
                        binding.currentWeatherErrorView.retryButton.setOnClickListener {
                            viewModel.loadData()
                        }
                    }
                    is WeatherState.ShowData -> {
                        binding.loading.visibility = View.GONE
                        binding.currentWeatherView.root.visibility = View.VISIBLE
                        binding.forecastView.root.visibility = View.VISIBLE
                        binding.currentWeatherErrorView.root.visibility = View.GONE

                        setTemperatureInfo(weatherState.data.temperatureInfo)
                        setMainInfo(weatherState.data.mainInfo[0]
                        )
                        binding.cityNameTextView.text = buildString {
                            append(weatherState.data.name)
                            append(", ")
                            append(weatherState.data.internalInfo.country ?: "")
                        }

                    }
                }
            }

        }

    }

    private fun setTemperatureInfo(
        temperatureInfo: TemperatureInfo
    ) {
        binding.currentWeatherView.humidityTextView.text = java.lang.String.format(
            Locale.getDefault(),
            "%d%%",
            temperatureInfo.humidity
        )
        binding.currentWeatherView.feelingTextView.text = java.lang.String.format(
            Locale.getDefault(),
            "%.0f°",
            temperatureInfo.feelingTemperature
        )
        binding.currentWeatherView.tempTextView.text = java.lang.String.format(
            Locale.getDefault(),
            "%.0f°",
            temperatureInfo.temperature
        )
    }

    private fun setMainInfo(
        mainInfo: MainInfo
    ) {
        binding.currentWeatherView.descriptionTextView.text =
            mainInfo.description

        val url = BuildConfig.IMAGE_URL +
                mainInfo.icon +
                getString(R.string.icon_suffix)
        Glide
            .with(binding.root)
            .load(
                url
            )
            .dontAnimate()
            .centerInside()
            .into(binding.currentWeatherView.icon)
    }

    private fun subscribeForecastResult() {
        lifecycleScope.launch {
            viewModel.forecastState.observe(this@MainActivity) { weatherState ->
                when (weatherState) {
                    is WeatherState.IsLoading -> {
                        binding.forecastView.loadingForecast.visibility = View.VISIBLE
                        binding.forecastView.nextDaysRecycler.visibility = View.GONE
                        binding.forecastView.forecastErrorView.root.visibility = View.GONE
                    }
                    is WeatherState.ShowError -> {
                        binding.forecastView.loadingForecast.visibility = View.GONE
                        binding.forecastView.nextDaysRecycler.visibility = View.GONE
                        binding.forecastView.forecastErrorView.root.visibility = View.VISIBLE

                        binding.forecastView.forecastErrorView.errorTextView.text = weatherState.message
                        binding.forecastView.forecastErrorView.retryButton.setOnClickListener {
                            viewModel.loadData()
                        }
                    }
                    is WeatherState.ShowData -> {
                        binding.forecastView.loadingForecast.visibility = View.GONE
                        binding.forecastView.forecastErrorView.root.visibility = View.GONE
                        binding.forecastView.root.visibility = View.VISIBLE
                        binding.forecastView.nextDaysRecycler.visibility = View.VISIBLE
                        adapter.submitList(weatherState.data.list)
                    }
                }
            }

        }

    }
}