package org.example.project.plugins


import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.actions.Gesture
import org.example.project.db.GestureService
import org.example.project.db.connectToPostgres
import java.sql.Connection
import java.time.Duration

fun Application.configureRouting() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val dbConnection: Connection = connectToPostgres(embedded = true)
    val gestureService = GestureService(dbConnection)


    routing {

        webSocket("/ws") {
            val gesture = Gesture()
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()

                if(receivedText.equals("getGestureParams", ignoreCase = true)){
                    CoroutineScope(Dispatchers.IO).launch {
                        while (true){
                            val gestureParams = gesture.generateGestureParams()
                            gestureService.create(gestureParams)
                            val message = "moveToX(${gestureParams.moveToX}), moveToY(${gestureParams.moveToY}), lineToX(${gestureParams.lineToX}), lineToY(${gestureParams.lineToY}), duration(${gestureParams.duration})"
                            send(Frame.Text(message))
                            delay(3000)
                        }
                    }

                }
            }
        }
    }

}