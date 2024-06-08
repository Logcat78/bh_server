package ui.screens

import actions.Launch
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.StaticData
import org.example.project.server


@Composable
fun App(onNavigate: () -> Unit) {
    val buttonText = remember { mutableStateOf("Старт") }
    buttonText.value = StaticData.buttonText


    val launch = Launch()
    MaterialTheme {
        Column(
            Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 50.dp),
                onClick = {
                println(StaticData.buttonState)
                if(!StaticData.buttonState){
                    StaticData.buttonState = true
                    server(true)

                    buttonText.value = "Пауза"
                    StaticData.buttonText = "Пауза"
                }else{
                    StaticData.buttonState = false
                    server(false)
                    buttonText.value = "Старт"
                    StaticData.buttonText = "Старт"
                }
            }) {
                Text(buttonText.value)
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 50.dp),
                onClick = {onNavigate()},
            ){
                Text("Логи сервера")
            }

        }
    }
}