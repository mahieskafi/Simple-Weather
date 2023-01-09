package com.eskafi.simple_weather

import com.eskafi.simple_weather.data.api.dto.*

class FakeWeatherResponse {
     fun makeWeather(): WeatherResponseDto {
        val temperatureInfo = TemperatureInfoDto(
            temperature = 6.14,
            feelingTemperature = 6.14,
            minTemperature = .03,
            maxTemperature = 9.68,
            humidity = 97
        )
        val mainInfo = mutableListOf(
            MainInfoDto(
                mainStatus = "Rain",
                description = "moderate rain",
                icon = "10d"
            )
        )

        val internalInfo = InternalInfoDto(
            country = "DE",
            sunrise = 1661834187,
            sunset = 1661882248
        )
        val coordinationInfo = CoordinationInfoDto(
            latitude = 44.34,
            longitude = 10.99
        )
        return WeatherResponseDto(
            id = 1,
            name = "Berlin",
            temperatureInfoDto = temperatureInfo,
            dateTime = 1661882400,
            mainInfo = mainInfo,
            internalInfo = internalInfo,
            coordinationInfo = coordinationInfo
        )
    }

    fun makeWeatherList():WeatherListResponseDto{
        return WeatherListResponseDto(mutableListOf(makeWeather()))
    }
}