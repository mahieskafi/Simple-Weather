package com.eskafi.simple_weather.domain.model


data class TemperatureInfo(

    val temperature: Double,

    val feelingTemperature: Double,

    val minTemperature: Double,

    val maxTemperature: Double,

    val humidity: Int
)
