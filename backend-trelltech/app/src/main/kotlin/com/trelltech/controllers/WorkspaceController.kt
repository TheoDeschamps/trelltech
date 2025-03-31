package com.trelltech.controllers

import com.trelltech.services.TrelloService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.workspaceRoutes() {
    get("/workspaces") {
        val userId = call.request.queryParameters["userId"]
            ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing userId")

        try {
            val workspaces = TrelloService().getWorkspaces(userId)
            call.respond(workspaces)
        } catch (e: Exception) {
            call.application.environment.log.error("Error fetching workspaces", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to fetch workspaces")
        }
    }
}
