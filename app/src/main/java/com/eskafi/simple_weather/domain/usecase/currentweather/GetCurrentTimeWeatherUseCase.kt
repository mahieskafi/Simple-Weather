package com.eskafi.simple_weather.domain.usecase.currentweather

import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.domain.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface GetCurrentTimeWeatherUseCase {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherResponse>>
}