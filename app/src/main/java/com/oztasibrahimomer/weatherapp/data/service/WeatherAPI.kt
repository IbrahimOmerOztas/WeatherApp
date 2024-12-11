package com.oztasibrahimomer.weatherapp.data.service

import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO
import com.oztasibrahimomer.weatherapp.util.Constants.APIKEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {


    @GET("current.json")
    suspend fun getWeather(

        @Query("q") city:String,
        @Query("key") apiKey:String=APIKEY

    ):Response<WeatherDTO>
}