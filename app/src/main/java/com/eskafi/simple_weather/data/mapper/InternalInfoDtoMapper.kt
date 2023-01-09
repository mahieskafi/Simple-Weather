package com.eskafi.simple_weather.data.mapper

import com.eskafi.simple_weather.data.api.dto.InternalInfoDto
import com.eskafi.simple_weather.domain.DomainMapper
import com.eskafi.simple_weather.domain.model.InternalInfo

class InternalInfoDtoMapper: DomainMapper<InternalInfoDto, InternalInfo> {

     override fun mapToDomainModel(model: InternalInfoDto): InternalInfo {
        return InternalInfo(
            country = model.country,
            sunset = model.sunset,
            sunrise = model.sunrise
        )
     }

     override fun mapFromDomainModel(domainModel: InternalInfo): InternalInfoDto {
         return InternalInfoDto(
             country = domainModel.country ?: "",
             sunset = domainModel.sunset,
             sunrise = domainModel.sunrise
         )
     }

}
