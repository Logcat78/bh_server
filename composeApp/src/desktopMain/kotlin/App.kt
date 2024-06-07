import actions.Launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import org.example.project.startServer

@Composable
fun App() {
    val buttonText = remember { mutableStateOf("Старт") }
    var buttonState = false
    val launch = Launch()
    MaterialTheme {
        Column(
            Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                println(buttonState)
                if(!buttonState){
                    buttonState = true
                    GlobalScope.launch {
                        startServer(true)
                    }

                    buttonText.value = "Пауза"
                }else{
                    startServer(false)
                    buttonText.value = "Старт"
                    buttonState = false
                }
            }) {
                Text(buttonText.value)
            }

        }
    }
}