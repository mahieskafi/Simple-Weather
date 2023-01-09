package com.eskafi.simple_weather.domain.usecase.forecasthourly

import com.eskafi.simple_weather.BuildConfig
import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.data.mapper.WeatherListResponseDtoMapper
import com.eskafi.simple_weather.domain.Constants
import com.eskafi.simple_weather.domain.model.WeatherListResponse
import com.eskafi.simple_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetForecastHourlyUseCaseImp(
    private val repository: WeatherRepository,
    private val weatherListMapper: WeatherListResponseDtoMapper
) : GetForecastHourlyUseCase{
    override suspend fun getForecastHourlyList(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherListResponse>> = flow {
        try {
            val response = repository.getForecastHourlyList(
                latitude,
                longitude,
                Constants.UNITS,
                BuildConfig.APP_KEY,
                8
            )
            if (response.isSuccessful && response.body()!= null )
                emit(Resource.Success(weatherListMapper.mapToDomainModel(response.body()!!)))
            else emit(Resource.Error(response.message()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}