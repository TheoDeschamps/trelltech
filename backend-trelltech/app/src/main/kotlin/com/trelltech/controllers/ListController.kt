package com.trelltech.controllers

import com.trelltech.services.TrelloService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.listRoutes(service: TrelloService) {

    route("/lists") {

        get("/boards/{boardId}") {
            val boardId = call.parameters["boardId"]
            val userId = call.request.queryParameters["userId"]

            if (boardId == null || userId == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing boardId or userId")
                return@get
            }

            try {
                val lists = service.getLists(boardId, userId)
                call.respond(lists)
            } catch (e: Exception) {
                call.application.environment.log.error("Error fetching lists", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to fetch lists")
            }
        }

        post {
            val params = call.receiveParameters()
            val boardId = params["boardId"]
            val name = params["name"]
            val userId = params["userId"]

            if (boardId.isNullOrBlank() || name.isNullOrBlank() || userId.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing boardId, name or userId")
                return@post
            }

            try {
                val newList = service.createList(boardId, name, userId)
                call.respond(HttpStatusCode.Created, newList)
            } catch (e: Exception) {
                call.application.environment.log.error("Error creating list", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to create list")
            }
        }

        put("/{listId}") {
            val listId = call.parameters["listId"]
            val params = call.receiveParameters()
            val name = params["name"]
            val userId = params["userId"]

            if (listId.isNullOrBlank() || userId.isNullOrBlank() || name.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing listId, name or userId")
                return@put
            }

            try {
                val updatedList = service.updateList(listId, name, userId)
                call.respond(updatedList)
            } catch (e: Exception) {
                call.application.environment.log.error("Error updating list", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to update list")
            }
        }

        delete("/{listId}") {
            val listId = call.parameters["listId"]
            val userId = call.request.queryParameters["userId"]

            if (listId.isNullOrBlank() || userId.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing listId or userId")
                return@delete
            }

            try {
                service.deleteList(listId, userId)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: Exception) {
                call.application.environment.log.error("Error deleting list", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to delete list")
            }
        }
    }
}
