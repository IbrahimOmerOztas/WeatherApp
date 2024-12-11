package com.oztasibrahimomer.weatherapp.domain.repository

import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO
import retrofit2.Response

interface WeatherRepository {

    suspend fun getWeather(city:String):Response<WeatherDTO>
}