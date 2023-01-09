package com.eskafi.simple_weather.data.api

import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastHourlyApi {
    @GET("forecast")
    suspend fun getForecastHourlyList(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") appId: String,
        @Query("cnt") listCount: Int
    ): Response<WeatherListResponseDto>
}
