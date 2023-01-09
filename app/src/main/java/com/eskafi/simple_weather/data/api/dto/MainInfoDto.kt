package com.eskafi.simple_weather.data.api.dto

import com.google.gson.annotations.SerializedName

data class MainInfoDto(

    @SerializedName("main")
    val mainStatus: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String

)
