package com.eskafi.simple_weather.domain

import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import com.eskafi.simple_weather.datasource.FakeRemoteWeatherDataSource
import com.eskafi.simple_weather.domain.repository.WeatherRepository
import retrofit2.Response

class FakeRepository(
    var weatherResponse: WeatherResponseDto? = null,
    var weatherListResponse: WeatherListResponseDto? = null
) : WeatherRepository {

    private lateinit var fakeRemoteDataSource: FakeRemoteWeatherDataSource

    override suspend fun getCurrentTimeWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String
    ): Response<WeatherResponseDto> {
        fakeRemoteDataSource = FakeRemoteWeatherDataSource(weatherResponse)
        return fakeRemoteDataSource.getCurrentTimeWeather(latitude, longitude, units, appId)
    }

    override suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String,
        listCount: Int
    ): Response<WeatherListResponseDto> {
        fakeRemoteDataSource = FakeRemoteWeatherDataSource(weatherListResponse = weatherListResponse)
        return fakeRemoteDataSource.getForecastHourlyList(latitude, longitude, units, appId,listCount)
    }
}