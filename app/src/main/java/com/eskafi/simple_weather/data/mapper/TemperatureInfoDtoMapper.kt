package com.eskafi.simple_weather.data.mapper

import com.eskafi.simple_weather.data.api.dto.TemperatureInfoDto
import com.eskafi.simple_weather.domain.DomainMapper
import com.eskafi.simple_weather.domain.model.TemperatureInfo

class TemperatureInfoDtoMapper : DomainMapper<TemperatureInfoDto, TemperatureInfo> {
    override fun mapToDomainModel(model: TemperatureInfoDto): TemperatureInfo {
        return TemperatureInfo(
            temperature = model.temperature,
            feelingTemperature = model.feelingTemperature,
            minTemperature = model.minTemperature,
            maxTemperature = model.maxTemperature,
            humidity = model.humidity
        )
    }

    override fun mapFromDomainModel(domainModel: TemperatureInfo): TemperatureInfoDto {
        return TemperatureInfoDto(
            temperature = domainModel.temperature,
            feelingTemperature = domainModel.feelingTemperature,
            minTemperature = domainModel.minTemperature,
            maxTemperature = domainModel.maxTemperature,
            humidity = domainModel.humidity
        )
    }
}