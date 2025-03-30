package com.trelltech.controllers

import com.trelltech.models.TrelloCard
import com.trelltech.services.TrelloService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.cardRoutes() {

    get("/lists/{listId}/cards") {
        val listId = call.parameters["listId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing listId")
        val userId = call.request.queryParameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing userId")

        try {
            val cards = TrelloService().getCards(listId, userId)
            call.respond(cards)
        } catch (e: Exception) {
            call.application.environment.log.error("Error fetching cards", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to fetch cards")
        }
    }

    post("/cards") {
        val params = call.receive<Map<String, String>>()
        val listId = params["listId"]
        val name = params["name"]
        val desc = params["desc"]
        val userId = params["userId"]

        if (listId == null || name == null || userId == null) {
            return@post call.respond(HttpStatusCode.BadRequest, "Missing required fields")
        }

        try {
            val card = TrelloService().createCard(listId, name, desc, userId)
            call.respond(HttpStatusCode.Created, card)
        } catch (e: Exception) {
            call.application.environment.log.error("Error creating card", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to create card")
        }
    }

    delete("/cards/{cardId}") {
        val cardId = call.parameters["cardId"]
        val userId = call.request.queryParameters["userId"]

        if (cardId == null || userId == null) {
            return@delete call.respond(HttpStatusCode.BadRequest, "Missing cardId or userId")
        }

        try {
            TrelloService().deleteCard(cardId, userId)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            call.application.environment.log.error("Error deleting card", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to delete card")
        }
    }

    post("/cards/{cardId}/assign") {
        val cardId = call.parameters["cardId"]
        val params = call.receive<Map<String, String>>()
        val memberId = params["memberId"]
        val userId = params["userId"]

        if (cardId == null || memberId == null || userId == null) {
            return@post call.respond(HttpStatusCode.BadRequest, "Missing required fields")
        }

        try {
            TrelloService().assignMember(cardId, memberId, userId)
            call.respond(HttpStatusCode.NoContent) // ✅ ou OK
        } catch (e: Exception) {
            call.application.environment.log.error("Error assigning member", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to assign member")
        }
    }

}
