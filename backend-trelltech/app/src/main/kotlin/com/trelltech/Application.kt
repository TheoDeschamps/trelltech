package com.trelltech

import com.trelltech.controllers.AuthController.authRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.origin
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

var logger = LoggerFactory.getLogger("Application")

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Trelltech API is running! on ${call.request.origin.host}")
            logger.info("Trelltech API is running! on ${call.request.origin.host}")
        }

        authRoutes()
    }
}
