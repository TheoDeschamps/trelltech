package com.trelltech.controllers

import com.trelltech.services.TrelloService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.boardRoutes(trelloService: TrelloService) {

    route("/boards") {

        get("/{userId}") {
            val userId = call.parameters["userId"]

            if (userId.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId")
                return@get
            }

            try {
                val boards = trelloService.getBoards(userId)
                call.respond(HttpStatusCode.OK, boards)
            } catch (e: Exception) {
                call.application.environment.log.error("Error fetching boards", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to fetch boards")
            }
        }
    }
}
