package com.eskafi.simple_weather.data.api.dto

import com.google.gson.annotations.SerializedName

data class CoordinationInfoDto(

    @SerializedName("lon")
    val longitude: Double,

    @SerializedName("lat")
    val latitude: Double
)
