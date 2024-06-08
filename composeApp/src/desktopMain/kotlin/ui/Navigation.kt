package ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.screens.App
import ui.screens.LogsScreen

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "app") {

        composable("app"){
            App {
                navController.navigate("logs")
            }

        }

        composable("logs"){
            LogsScreen  {
                navController.navigate("app")
            }
        }

    }
}