package com.eskafi.simple_weather.domain.usecase

import com.eskafi.simple_weather.FakeWeatherResponse
import com.eskafi.simple_weather.MainCoroutineRule
import com.eskafi.simple_weather.data.api.dto.WeatherListResponseDto
import com.eskafi.simple_weather.data.mapper.WeatherListResponseDtoMapper
import com.eskafi.simple_weather.domain.FakeRepository
import com.eskafi.simple_weather.domain.usecase.forecasthourly.GetForecastHourlyUseCaseImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Math.abs

@ExperimentalCoroutinesApi
class GetForecastHourlyUseCaseTest {
    private lateinit var weatherRepository: FakeRepository
    private lateinit var getForecastHourlyUseCase: GetForecastHourlyUseCaseImp

    private lateinit var weatherListMock: WeatherListResponseDto

    private val latitude = 44.34
    private val longitude = 10.99
    private val units = "metric"
    private val appId = "fakeId"

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        weatherListMock = FakeWeatherResponse().makeWeatherList()

        weatherRepository = FakeRepository(weatherListResponse = weatherListMock)
        getForecastHourlyUseCase = GetForecastHourlyUseCaseImp(
            weatherRepository,
            WeatherListResponseDtoMapper()
        )
    }


    @Test
    fun getWeather_getForecastWeatherFromRemoteDataSource_success() = runTest {

        val weatherListResponse =
            weatherRepository.getForecastHourlyList(latitude, longitude, units, appId, 1)

        assertEquals(weatherListResponse.isSuccessful, true)

        val result = weatherListResponse.body()

        assertEquals(result!!.list.size, 1)
        assertEquals(
            result, weatherListMock
        )
        assertEquals(
            result.list[0].coordinationInfo!!.latitude,
            latitude,
            abs(result.list[0].coordinationInfo!!.latitude - latitude)
        )
    }

    @Test
    fun getWeather_getForecastWeather_error() = runTest {
        weatherRepository.weatherListResponse = null
        val weatherListResponse =
            weatherRepository.getForecastHourlyList(latitude, longitude, units, appId, 1)


        assertEquals(weatherListResponse.isSuccessful, false)
        assertEquals(weatherListResponse.code(), 1001)
    }
}