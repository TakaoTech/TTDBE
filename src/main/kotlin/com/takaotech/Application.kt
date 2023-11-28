package com.takaotech

import com.takaotech.plugins.configureHTTP
import com.takaotech.plugins.configureMonitoring
import com.takaotech.plugins.configureRouting
import com.takaotech.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*

fun main() {
    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
