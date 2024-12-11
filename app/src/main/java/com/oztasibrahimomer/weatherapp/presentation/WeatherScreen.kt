package com.oztasibrahimomer.weatherapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel= hiltViewModel()
) {

    val state = viewModel.state.value
    var textTf by remember{ mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFC5F7C7))
    )
    {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)){
            TextField(
                value = textTf,
                onValueChange = {textTf=it},
                label = { Text(text = "City", fontSize = 13.sp, fontWeight = FontWeight.SemiBold,color=Color.DarkGray)},
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(Event.GetStringWeather(textTf))
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color(0xFFA9ECF5)


                ),
                maxLines = 1,



                )
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 130.dp, end = 10.dp, bottom = 20.dp)
                .border(3.dp, color = Color.Red),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){


            if(state.isLoading && state.error.isEmpty()){

                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp,30.dp),
                    color=Color.Red
                )
            }
            else if(!state.isLoading && state.error.isNotEmpty()){

                Text(text =state.error.toString(), fontSize = 22.sp, fontWeight = FontWeight.ExtraBold,color= Color.Red, textAlign = TextAlign.Center)


            }

            if(state.dto != null){

                WeatherDetail(data = state.dto)
            }







        }



    }

}

@Composable
fun WeatherDetail(data:WeatherDTO) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ){

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "",
                modifier=Modifier.size(40.dp)
            )

            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)



        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            
            Text(text = "${data.current.temp_c}° C", fontSize = 45.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        
        Spacer(modifier = Modifier.height(25.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            
            AsyncImage(
                model = "https:"+data.current.condition.icon,
                contentDescription ="" ,
                modifier = Modifier.border(2.dp,color=Color.Blue)
            )



        }

    }

}