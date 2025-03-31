package com.trelltech

import com.trelltech.config.DatabaseFactory
import com.trelltech.controllers.AuthController.authRoutes
import com.trelltech.controllers.boardRoutes
import com.trelltech.controllers.cardRoutes
import com.trelltech.controllers.listRoutes
import com.trelltech.controllers.tokenRoutes
import com.trelltech.controllers.workspaceRoutes
import com.trelltech.services.TokenService
import com.trelltech.services.TrelloService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.origin
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

var logger = LoggerFactory.getLogger("Application")

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}


fun Application.module() {

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    DatabaseFactory.init()
    val tokenService = TokenService()
    val trelloService = TrelloService()
    routing {
        get("/") {
            call.respondText("Trelltech API is running! on ${call.request.origin.host}")
            logger.info("Trelltech API is running! on ${call.request.origin.host}")
        }

        authRoutes()
        tokenRoutes(tokenService)
        boardRoutes(trelloService)
        listRoutes()
        cardRoutes()
        workspaceRoutes()
    }
}
