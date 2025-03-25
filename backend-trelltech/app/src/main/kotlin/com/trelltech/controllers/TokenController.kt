package com.trelltech.controllers

import com.trelltech.services.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tokenRoutes(tokenService: TokenService) {
    route("/tokens") {
        post {
            val body = call.receive<Map<String, String>>()
            val userId = body["userId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing userId")
            val token = body["token"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing token")

            tokenService.saveToken(userId, token)
            call.respond(HttpStatusCode.OK, "Token saved")
        }

        get("/{userId}") {
            val userId = call.parameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val token = tokenService.getToken(userId)
            if (token != null)
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            else
                call.respond(HttpStatusCode.NotFound, "Token not found")
        }
    }
}
