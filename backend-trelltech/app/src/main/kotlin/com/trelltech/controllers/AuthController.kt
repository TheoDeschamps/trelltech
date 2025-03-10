package com.trelltech.controllers

import com.trelltech.config.SecurityConfig
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

object AuthController {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)
    private val tokenStorage = ConcurrentHashMap<String, String>()

    fun Route.authRoutes() {
        route("/auth") {
            get("/login") {
                val authUrl = SecurityConfig.getAuthUrl()
                call.respondRedirect(authUrl)
            }

            get("/callback") {
                val token = call.request.queryParameters["token"]

                if (token.isNullOrBlank()) {
                    logger.error("Token manquant dans la requête")
                    call.respond(HttpStatusCode.BadRequest, "Erreur : Aucun token fourni par Trello")
                    return@get
                }

                tokenStorage["user"] = token
                logger.info("Token OAuth Trello reçu et stocké : $token")

                call.respondText("Authentification réussie ! Token reçu : $token")
            }
        }
    }
}
