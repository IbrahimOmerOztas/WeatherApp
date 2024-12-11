package com.oztasibrahimomer.weatherapp.domain.useCase

import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO
import com.oztasibrahimomer.weatherapp.domain.repository.WeatherRepository
import com.oztasibrahimomer.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {



    fun executeGetWeather(city:String) : Flow<Resource<Response<WeatherDTO>>> = flow{

        try {
            emit(Resource.Loading())

            if(repository.getWeather(city = city).isSuccessful){

                emit(Resource.Success(repository.getWeather(city = city)))
            }
            else{

                emit(Resource.Error(message = "not found data!!"))

            }

        }
        catch (e:Exception) {

            emit(Resource.Error(e.message ?: "not found data"))
        }


    }
}