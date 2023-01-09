package com.eskafi.simple_weather.data.api.dto

import com.google.gson.annotations.SerializedName

data class WeatherListResponseDto(

    @SerializedName("list")
    val list: List<WeatherResponseDto>,
)

