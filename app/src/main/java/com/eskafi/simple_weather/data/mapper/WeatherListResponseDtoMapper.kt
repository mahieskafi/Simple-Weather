package com.eskafi.simple_weather.data.mapper

import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.domain.DomainMapper
import com.eskafi.simple_weather.domain.model.WeatherListResponse

class WeatherListResponseDtoMapper : DomainMapper<WeatherListResponseDto, WeatherListResponse> {
    override fun mapToDomainModel(model: WeatherListResponseDto): WeatherListResponse {
        return WeatherListResponse(
            model.list.map { weatherResponseDto ->
                WeatherResponseDtoMapper().mapToDomainModel(
                    weatherResponseDto
                )
            }.toList()
        )
    }

    override fun mapFromDomainModel(domainModel: WeatherListResponse): WeatherListResponseDto {
        return WeatherListResponseDto(
            domainModel.list.map { weatherResponse ->
                WeatherResponseDtoMapper().mapFromDomainModel(
                    weatherResponse
                )
            }.toList()
        )
    }
}