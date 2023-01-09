package com.eskafi.simple_weather.data.repository

import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import com.eskafi.simple_weather.domain.repository.WeatherRepository
import com.eskafi.simple_weather.data.repository.datasource.WeatherRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val dataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun getCurrentTimeWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String
    ): Response<WeatherResponseDto> =
        dataSource.getCurrentTimeWeather(
            latitude,
            longitude,
            units,
            appId
        )

    override suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String,
        listCount: Int
    ): Response<WeatherListResponseDto> =
        dataSource.getForecastHourlyList(
            latitude,
            longitude,
            units,
            appId,
            listCount
        )
}