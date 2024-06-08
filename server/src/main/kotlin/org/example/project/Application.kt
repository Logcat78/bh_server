package org.example.project

import SERVER_PORT
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.actions.Gesture
import org.example.project.db.DbData
import org.example.project.db.GestureService
import org.example.project.db.connectToPostgres
import java.sql.Connection
import java.time.Duration


var index = 1
var serverStatus1 = false

val server = embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0"){
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    val dbConnection: Connection = connectToPostgres(embedded = true)
    val gestureService = GestureService(dbConnection)

    fun getLog(index: Int){
        CoroutineScope(Dispatchers.IO).launch {
            DbData.log = gestureService.read(index).toString()
        }
    }
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
                            if(!serverStatus1)
                                send(Frame.Text(message))
                            println(index)
                            delay(3000)
                            getLog(index = index)
                            index++
                        }
                    }

                }
            }
        }
    }
}.start()


fun server(serverStatus: Boolean) {
    if(serverStatus){
        serverStatus1 = false

    }else {
        serverStatus1 = true

    }
}

