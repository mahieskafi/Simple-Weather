package com.eskafi.simple_weather.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eskafi.simple_weather.FakeWeatherResponse
import com.eskafi.simple_weather.MainCoroutineRule
import com.eskafi.simple_weather.data.Resource
import com.eskafi.simple_weather.data.location.LocationTrackerImpl
import com.eskafi.simple_weather.data.mapper.WeatherListResponseDtoMapper
import com.eskafi.simple_weather.data.mapper.WeatherResponseDtoMapper
import com.eskafi.simple_weather.domain.location.LocationTracker
import com.eskafi.simple_weather.domain.model.WeatherListResponse
import com.eskafi.simple_weather.domain.model.WeatherResponse
import com.eskafi.simple_weather.presentation.WeatherState
import com.eskafi.simple_weather.presentation.weather.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var fakeGetCurrentTimeWeatherUseCase: FakeGetCurrentWeatherUseCase
    private lateinit var fakeGetForecastHourlyUseCase: FakeGetForecastHourlyUseCase

    private lateinit var currentWeatherMock: WeatherResponse
    private lateinit var forecastWeatherMock: WeatherListResponse

    private val latitude = 44.34
    private val longitude = 10.99

    companion object {
        private const val DELAY_TIME = 3000L
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var locationTracker: LocationTracker
    @Mock
    lateinit var  application: Application
    @Mock
    lateinit var  locationClient :FusedLocationProviderClient

    @Before
    fun setUpViewModel() {

        currentWeatherMock = WeatherResponseDtoMapper().mapToDomainModel(
            FakeWeatherResponse().makeWeather()
        )
        forecastWeatherMock = WeatherListResponseDtoMapper().mapToDomainModel(
            FakeWeatherResponse().makeWeatherList()
        )

        application = mock(Application::class.java)
        locationClient = mock(FusedLocationProviderClient::class.java)
        locationTracker = LocationTrackerImpl(locationClient, application)

        fakeGetCurrentTimeWeatherUseCase =
            FakeGetCurrentWeatherUseCase(Resource.Success(currentWeatherMock))
        fakeGetForecastHourlyUseCase =
            FakeGetForecastHourlyUseCase(Resource.Success(forecastWeatherMock))

        weatherViewModel = WeatherViewModel(
            fakeGetCurrentTimeWeatherUseCase,
            fakeGetForecastHourlyUseCase,
            locationTracker
        )
    }

    @Test
    fun getWeather_getCurrentWeather_success() = runTest {
        assertEquals(weatherViewModel.currentWeatherState.value, WeatherState.IsLoading)

        weatherViewModel.loadCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )
        delay(DELAY_TIME)
        assertTrue(weatherViewModel.currentWeatherState.value is WeatherState.ShowData)
        assertEquals(
            (weatherViewModel.currentWeatherState.value as WeatherState.ShowData).data,
            currentWeatherMock
        )
        assertFalse(weatherViewModel.currentWeatherState.value is WeatherState.IsLoading)
        assertFalse(weatherViewModel.currentWeatherState.value is WeatherState.ShowError)
    }

    @Test
    fun getWeather_getCurrentWeather_error() = runTest {

        fakeGetCurrentTimeWeatherUseCase =
            FakeGetCurrentWeatherUseCase(null)

        weatherViewModel = WeatherViewModel(
            fakeGetCurrentTimeWeatherUseCase,
            fakeGetForecastHourlyUseCase,
            locationTracker
        )

        assertEquals(weatherViewModel.currentWeatherState.value, WeatherState.IsLoading)

        weatherViewModel.loadCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )
        delay(DELAY_TIME)
        assertFalse(weatherViewModel.currentWeatherState.value is WeatherState.ShowData)
        assertFalse(weatherViewModel.currentWeatherState.value is WeatherState.IsLoading)
        assertTrue(weatherViewModel.currentWeatherState.value is WeatherState.ShowError)
    }

    @Test
    fun getWeather_getForecastList_success() = runTest {

        weatherViewModel.loadForecastList(
            latitude = latitude,
            longitude = longitude
        )
        delay(DELAY_TIME)
        assertTrue(weatherViewModel.forecastState.value is WeatherState.ShowData)
        assertEquals(
            (weatherViewModel.forecastState.value as WeatherState.ShowData).data,
            forecastWeatherMock
        )
        assertFalse(weatherViewModel.forecastState.value is WeatherState.IsLoading)
        assertFalse(weatherViewModel.forecastState.value is WeatherState.ShowError)
    }

    @Test
    fun getWeather_getForecastList_error() = runTest {

        fakeGetForecastHourlyUseCase =
            FakeGetForecastHourlyUseCase(null)

        weatherViewModel = WeatherViewModel(
            fakeGetCurrentTimeWeatherUseCase,
            fakeGetForecastHourlyUseCase,
            locationTracker
        )

        weatherViewModel.loadForecastList(
            latitude = latitude,
            longitude = longitude
        )
        delay(DELAY_TIME)
        assertFalse(weatherViewModel.forecastState.value is WeatherState.ShowData)
        assertFalse(weatherViewModel.forecastState.value is WeatherState.IsLoading)
        assertTrue(weatherViewModel.forecastState.value is WeatherState.ShowError)
    }
}