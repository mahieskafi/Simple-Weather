package com.eskafi.simple_weather.domain.model


data class WeatherResponse(

    val id: Long,

    val name: String?,

    val temperatureInfo: TemperatureInfo,

    val mainInfo: List<MainInfo>,

    val dateTime: Long,

    val internalInfo: InternalInfo,

    val coordinationInfo: CoordinationInfo?

)
