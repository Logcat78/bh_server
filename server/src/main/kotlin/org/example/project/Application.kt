package org.example.project

import Greeting
import SERVER_PORT
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.db.configureDatabases
import org.example.project.plugins.configureRouting




fun startServer(serverStatus: Boolean) {
    val server = embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
    CoroutineScope(Dispatchers.IO).launch {
        if(serverStatus){
            server.start()
        }else{
            println("daaa")
            server.stop(0,0)
        }
    }



}

fun Application.module() {
    configureDatabases()
}