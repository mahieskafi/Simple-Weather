package com.eskafi.simple_weather.data.mapper

import com.eskafi.simple_weather.data.api.dto.CoordinationInfoDto
import com.eskafi.simple_weather.data.api.dto.WeatherResponseDto
import com.eskafi.simple_weather.domain.DomainMapper
import com.eskafi.simple_weather.domain.model.CoordinationInfo
import com.eskafi.simple_weather.domain.model.WeatherResponse

class WeatherResponseDtoMapper : DomainMapper<WeatherResponseDto, WeatherResponse> {
    override fun mapToDomainModel(model: WeatherResponseDto): WeatherResponse {
        val coordinatorInfo :CoordinationInfo? =
            if(model.coordinationInfo!= null) CoordinationInfoDtoMapper().mapToDomainModel(
            model.coordinationInfo
        ) else null

        return WeatherResponse(
            id = model.id,
            name = model.name,
            temperatureInfo = TemperatureInfoDtoMapper().mapToDomainModel(
                model.temperatureInfoDto
            ),
            mainInfo = model.mainInfo.map { mainInfoDto ->
                MainInfoDtoMapper().mapToDomainModel(
                    mainInfoDto
                )
            }.toList(),
            dateTime = model.dateTime,
            internalInfo = InternalInfoDtoMapper().mapToDomainModel(
                model.internalInfo
            ),
            coordinationInfo =  coordinatorInfo
        )
    }

    override fun mapFromDomainModel(domainModel: WeatherResponse): WeatherResponseDto {
        val coordinatorInfoDto :CoordinationInfoDto? =
            if(domainModel.coordinationInfo!= null) CoordinationInfoDtoMapper().mapFromDomainModel(
                domainModel.coordinationInfo
            ) else null

        return WeatherResponseDto(
            id = domainModel.id,
            name = domainModel.name ?: "",
            temperatureInfoDto = TemperatureInfoDtoMapper().mapFromDomainModel(
                domainModel.temperatureInfo
            ),
            mainInfo = domainModel.mainInfo.map { mainInfo ->
                MainInfoDtoMapper().mapFromDomainModel(
                    mainInfo
                )
            }.toList(),
            dateTime = domainModel.dateTime,
            internalInfo = InternalInfoDtoMapper().mapFromDomainModel(
                domainModel.internalInfo
            ),
            coordinationInfo = coordinatorInfoDto
        )
    }
}
