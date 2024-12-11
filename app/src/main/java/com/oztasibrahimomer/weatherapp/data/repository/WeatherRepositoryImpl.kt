package com.oztasibrahimomer.weatherapp.data.repository

import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO
import com.oztasibrahimomer.weatherapp.data.service.WeatherAPI
import com.oztasibrahimomer.weatherapp.domain.repository.WeatherRepository
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api:WeatherAPI) : WeatherRepository {


    override suspend fun getWeather(city: String): Response<WeatherDTO> {
        return api.getWeather(city=city)
    }
}