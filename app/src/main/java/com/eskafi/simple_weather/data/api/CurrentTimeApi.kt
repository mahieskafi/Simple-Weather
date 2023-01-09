package com.eskafi.simple_weather.data.api

import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentTimeApi {

    @GET("weather")
    suspend fun getCurrentTimeWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Response<WeatherResponseDto>
}