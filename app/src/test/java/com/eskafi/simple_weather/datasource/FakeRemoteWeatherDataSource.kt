package com.eskafi.simple_weather.datasource

import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import com.eskafi.simple_weather.data.repository.datasource.WeatherRemoteDataSource
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class FakeRemoteWeatherDataSource(
    var weatherResponse: WeatherResponseDto? = null,
    var weatherListResponse: WeatherListResponseDto? = null
) : WeatherRemoteDataSource {

    override suspend fun getCurrentTimeWeather(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String
    ): Response<WeatherResponseDto> {
        weatherResponse?.let {
            return Response.success(it)
        }
        return Response.error(
            1001
            , ResponseBody.create(
                MediaType.parse("UTF-8"),
                "response not found"
            )
        )
    }

    override suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double,
        units: String,
        appId: String,
        listCount: Int
    ): Response<WeatherListResponseDto> {
        weatherListResponse?.let {
            return Response.success(it)
        }
        return Response.error(
            1001
            , ResponseBody.create(
                MediaType.parse("UTF-8"),
                "response not found"
            )
        )
    }
}