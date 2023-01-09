package com.eskafi.simple_weather.data.mapper

import com.eskafi.simple_weather.data.api.dto.CoordinationInfoDto
import com.eskafi.simple_weather.domain.DomainMapper
import com.eskafi.simple_weather.domain.model.CoordinationInfo

class CoordinationInfoDtoMapper : DomainMapper<CoordinationInfoDto, CoordinationInfo> {

    override fun mapToDomainModel(model: CoordinationInfoDto): CoordinationInfo {
        return CoordinationInfo(
            longitude = model.longitude,
            latitude = model.latitude
        )
    }

    override fun mapFromDomainModel(domainModel: CoordinationInfo): CoordinationInfoDto {
        return CoordinationInfoDto(
            longitude = domainModel.longitude,
            latitude = domainModel.latitude
        )
    }
}