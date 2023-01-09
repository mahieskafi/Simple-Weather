package com.eskafi.simple_weather.data.repository.datasourceImpl

import com.eskafi.simple_weather.data.api.CurrentTimeApi
import com.eskafi.simple_weather.data.api.ForecastHourlyApi
import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import com.eskafi.simple_weather.data.repository.datasource.WeatherRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSourceImp @Inject constructor(
    private val currentTimeApi: CurrentTimeApi,
    private val forecastHourlyApi : ForecastHourlyApi
) : WeatherRemoteDataSource {
    override suspend fun getCurrentTimeWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String
    ): Response<WeatherResponseDto> =
       currentTimeApi.getCurrentTimeWeather(
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
        forecastHourlyApi.getForecastHourlyList(
            latitude,
            longitude,
            units,
            appId,
            listCount
        )

}