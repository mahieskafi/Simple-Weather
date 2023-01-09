package com.eskafi.simple_weather.data.mapper

import com.eskafi.simple_weather.data.api.dto.MainInfoDto
import com.eskafi.simple_weather.domain.DomainMapper
import com.eskafi.simple_weather.domain.model.MainInfo

class MainInfoDtoMapper : DomainMapper<MainInfoDto,MainInfo> {
    override fun mapToDomainModel(model: MainInfoDto): MainInfo {
       return MainInfo(
           mainStatus = model.mainStatus,
           description = model.description,
           icon = model.icon
       )
    }

    override fun mapFromDomainModel(domainModel: MainInfo): MainInfoDto {
        return MainInfoDto(
            mainStatus = domainModel.mainStatus,
            description = domainModel.description,
            icon = domainModel.icon
        )
    }
}