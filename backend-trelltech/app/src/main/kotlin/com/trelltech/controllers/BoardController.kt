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

        post {
            val userId = call.parameters["userId"]
            val request = call.receive<Map<String, String>>()
            val boardName = request["name"]


            if (userId.isNullOrBlank() || boardName.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId or board name")
                return@post
            }

            try {
                val newBoard = trelloService.createBoard(boardName, userId)
                call.respond(HttpStatusCode.Created, newBoard)
            } catch (e: Exception) {
                call.application.environment.log.error("Error creating board", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to create board")
            }
        }

        delete("/{boardId}") {
            val boardId = call.parameters["boardId"]
            val userId = call.parameters["userId"]

            if (boardId.isNullOrBlank() || userId.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid boardId or userId")
                return@delete
            }

            try {
                trelloService.deleteBoard(boardId, userId)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: Exception) {
                call.application.environment.log.error("Error deleting board", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to delete board")
            }
        }

        put("/{boardId}") {
            val boardId = call.parameters["boardId"]
            val userId = call.parameters["userId"]
            val body = call.receive<Map<String, String>>()
            val boardName = body["name"]
            val boardDesc = body["desc"]


            if (boardId.isNullOrBlank() || userId.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid boardId or userId")
                return@put
            }

            try {
                val updatedBoard = trelloService.updateBoard(boardId, boardName, boardDesc, userId)
                call.respond(HttpStatusCode.OK, updatedBoard)
            } catch (e: Exception) {
                call.application.environment.log.error("Error updating board", e)
                call.respond(HttpStatusCode.InternalServerError, "Unable to update board")
            }
        }
    }
}
