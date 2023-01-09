package com.eskafi.simple_weather.domain.usecase.forecasthourly

import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.domain.model.WeatherListResponse
import kotlinx.coroutines.flow.Flow

interface GetForecastHourlyUseCase {
    suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherListResponse>>
}