package com.eskafi.simple_weather.domain.usecase.currentweather

import com.eskafi.simple_weather.BuildConfig
import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.data.mapper.WeatherResponseDtoMapper
import com.eskafi.simple_weather.domain.Constants.UNITS
import com.eskafi.simple_weather.domain.model.WeatherResponse
import com.eskafi.simple_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentTimeWeatherUseCaseImp @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherMapper: WeatherResponseDtoMapper
) : GetCurrentTimeWeatherUseCase {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherResponse>> = flow {
        try {
            val response = repository.getCurrentTimeWeather(
                latitude,
                longitude,
                UNITS,
                BuildConfig.APP_KEY
            )
            if (response.isSuccessful)
                emit(Resource.Success(weatherMapper.mapToDomainModel(response.body()!!)))
            else emit(Resource.Error(response.message()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}