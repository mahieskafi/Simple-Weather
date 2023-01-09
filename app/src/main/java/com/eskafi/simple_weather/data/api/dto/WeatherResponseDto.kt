package com.eskafi.simple_weather.data.api.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("main")
    val temperatureInfoDto: TemperatureInfoDto,

    @SerializedName("weather")
    val mainInfo: List<MainInfoDto>,

    @SerializedName("dt")
    val dateTime: Long,

    @SerializedName("sys")
    val internalInfo: InternalInfoDto,

    @SerializedName("coord")
    val coordinationInfo: CoordinationInfoDto?

)
