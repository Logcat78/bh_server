package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.StaticData
import org.example.project.db.DbData


@Composable
fun LogsScreen(onNavigate: () -> Unit){
    val logs = remember { mutableStateOf("") }
    logs.value = StaticData.serverLogs
    MaterialTheme{
        Column(
            modifier =
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 50.dp),
                onClick = {
                    if(!logs.value.contains(DbData.log)){
                        logs.value += "\n ${DbData.log}"
                        StaticData.serverLogs = logs.value
                    }
                },
            ){
                Text("Обновить логи")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 50.dp),
                onClick = {onNavigate()},
            ){
                Text("Назад")
            }
            LazyColumn {
                item {
                    Surface(
                        color = Color.LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = logs.value,
                            modifier = Modifier
                                .padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }


    }
}