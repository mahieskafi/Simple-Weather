package com.eskafi.simple_weather.viewmodel

import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.domain.model.WeatherResponse
import com.eskafi.simple_weather.domain.usecase.currentweather.GetCurrentTimeWeatherUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetCurrentWeatherUseCase(
    private val testValue: Resource<WeatherResponse>?
) : GetCurrentTimeWeatherUseCase {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherResponse>> {
        return flow {
            delay(3000L)
            if (testValue == null)
                emit(Resource.Error("error"))
            else
                emit(testValue)
        }
    }

}