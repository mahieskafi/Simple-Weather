package com.eskafi.simple_weather.data.api.dto

import com.google.gson.annotations.SerializedName

data class InternalInfoDto(

    @SerializedName("country")
    val country: String?,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)
