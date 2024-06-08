import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.Navigate
import ui.screens.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        Navigate()
    }
}