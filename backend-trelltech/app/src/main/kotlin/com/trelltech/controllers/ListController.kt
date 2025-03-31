package com.trelltech.controllers

import com.trelltech.services.TrelloService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.listRoutes() {
    get("/boards/{boardId}/lists") {
        val boardId = call.parameters["boardId"]
        val userId = call.request.queryParameters["userId"]

        if (boardId == null || userId == null) {
            call.respond(HttpStatusCode.BadRequest, "Missing boardId or userId")
            return@get
        }

        try {
            val service = TrelloService()
            val lists = service.getLists(boardId, userId)
            call.respond(lists)
        } catch (e: Exception) {
            call.application.environment.log.error("Error fetching lists", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to fetch lists")
        }
    }
}
