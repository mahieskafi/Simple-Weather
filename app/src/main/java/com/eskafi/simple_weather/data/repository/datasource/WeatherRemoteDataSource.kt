package com.eskafi.simple_weather.data.repository.datasource

import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import retrofit2.Response

interface WeatherRemoteDataSource {
    suspend fun getCurrentTimeWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String
    ): Response<WeatherResponseDto>

    suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String,
        listCount: Int
    ): Response<WeatherListResponseDto>
}