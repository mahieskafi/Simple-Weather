package com.eskafi.simple_weather.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.domain.location.LocationTracker
import com.eskafi.simple_weather.domain.model.WeatherListResponse
import com.eskafi.simple_weather.domain.model.WeatherResponse
import com.eskafi.simple_weather.domain.usecase.currentweather.GetCurrentTimeWeatherUseCase
import com.eskafi.simple_weather.domain.usecase.forecasthourly.GetForecastHourlyUseCase
import com.eskafi.simple_weather.presentation.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentTimeWeatherUseCase,
    private val getForecastHourlyUseCase: GetForecastHourlyUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _currentWeatherState = MutableLiveData<WeatherState<WeatherResponse>>()
    val currentWeatherState: LiveData<WeatherState<WeatherResponse>> = _currentWeatherState

    private val _forecastState = MutableLiveData<WeatherState<WeatherListResponse>>()
    val forecastState: LiveData<WeatherState<WeatherListResponse>> = _forecastState

    init {
        _currentWeatherState.value = WeatherState.IsLoading
    }

    fun loadData() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                loadCurrentWeather(location.latitude, location.longitude)
                loadForecastList(location.latitude, location.longitude)
            } ?: kotlin.run {
                _currentWeatherState.value =
                    WeatherState.ShowError("Couldn't retrieve location. Make sure to grant permission and enable GPS.")
            }
        }
    }

     suspend fun loadForecastList(
        latitude: Double,
        longitude: Double
    ) {
        _forecastState.value = WeatherState.IsLoading

        getForecastHourlyUseCase.getForecastHourlyList(
            latitude,
            longitude
        ).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _forecastState.value = WeatherState.ShowData(response.data!!)
                }
                is Resource.Error -> {
                    _forecastState.value = WeatherState.ShowError(response.exception)
                }
            }

        }.launchIn(viewModelScope)
    }

     suspend fun loadCurrentWeather(
        latitude: Double,
        longitude: Double
    ) {
        _currentWeatherState.value = WeatherState.IsLoading

        getCurrentWeatherUseCase.getCurrentWeather(
            latitude,
            longitude
        ).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _currentWeatherState.value = WeatherState.ShowData(resource.data!!)
                }
                is Resource.Error -> {
                    _currentWeatherState.value = WeatherState.ShowError(resource.exception)
                }
            }
        }.launchIn(viewModelScope)
    }
}