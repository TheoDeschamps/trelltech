package com.trelltech.controllers

import com.trelltech.services.TrelloService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.memberRoutes(trelloService: TrelloService) {

    get("/members/{memberId}") {
        val memberId = call.parameters["memberId"]
        val userId = call.request.queryParameters["userId"]

        if (memberId.isNullOrBlank() || userId.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Missing memberId or userId")
            return@get
        }

        try {
            val member = trelloService.getMember(memberId, userId)
            call.respond(member)
        } catch (e: Exception) {
            call.application.environment.log.error("Error fetching member", e)
            call.respond(HttpStatusCode.InternalServerError, "Unable to fetch member")
        }
    }
}
