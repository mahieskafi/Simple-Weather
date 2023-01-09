package com.eskafi.simple_weather.viewmodel

import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.domain.model.WeatherListResponse
import com.eskafi.simple_weather.domain.usecase.forecasthourly.GetForecastHourlyUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetForecastHourlyUseCase(
    private val testValue: Resource<WeatherListResponse>?
) : GetForecastHourlyUseCase {

    override suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherListResponse>> {
        return flow {
            delay(3000L)
            if (testValue == null)
                emit(Resource.Error("error"))
            else
                emit(testValue)
        }
    }

}