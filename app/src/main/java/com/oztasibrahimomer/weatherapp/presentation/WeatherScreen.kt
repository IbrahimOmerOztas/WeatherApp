package com.oztasibrahimomer.weatherapp.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    hint:String="",
    viewModel: WeatherViewModel= hiltViewModel()
) {

    val state = viewModel.state.value
    var textTf by remember{ mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF189FB1), Color(0xFF24C42B))
                )
            )
    )
    {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)){
            TextField(
                value = textTf,
                onValueChange = {textTf=it},
                placeholder = {Text(text = "City", fontSize = 13.sp, fontWeight = FontWeight.SemiBold,color=Color.Gray)},
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
                    containerColor = Color(0xFFCCD0D1)


                ),
                maxLines = 1,



                )
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 130.dp, end = 10.dp, bottom = 20.dp),
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

    val context= LocalContext.current

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
            Text(text = data.location.country, fontSize = 18.sp, color = Color.DarkGray)



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
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            
            Image(
                painter = rememberAsyncImagePainter(model = "https:${data.current.condition.icon}".replace("64x64","128x128")),
                contentDescription ="",
                modifier = Modifier.size(150.dp,150.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = data.current.condition.text,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )


        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier= Modifier
                .fillMaxWidth()
                .height(280.dp),
            colors = CardDefaults.cardColors(Color.LightGray),
            shape = RoundedCornerShape(10.dp)
        ){

            Column(

                modifier= Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    feature(value = "Humidity", feature = data.current.humidity.toString())
                    feature(value ="Wind Kph", feature = data.current.wind_kph.toString())

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    feature(value = "Cloud", feature = data.current.cloud.toString()+"%")
                    feature(value ="Local Time", feature = data.location.localtime.replace("2024-12-12",""))

                }








            }

        }


    }

}

@Composable
fun feature(value:String,feature:String) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        Text(text = value, fontSize = 25.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Text(text = feature, fontSize = 20.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)


    }


    
}