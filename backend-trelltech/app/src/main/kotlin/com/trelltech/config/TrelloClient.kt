package com.trelltech.config

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv
import com.trelltech.models.TrelloBoard
import io.ktor.client.call.body

class TrelloClient(private val token: String) {

    private val dotenv = dotenv()
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private val baseUrl = "https://api.trello.com/1"
    private val apiKey = dotenv["TRELLO_CLIENT_ID"] ?: error("Missing TRELLO_CLIENT_ID in env")

    suspend fun getBoards(): List<TrelloBoard> {
        val response: HttpResponse = client.get("$baseUrl/members/me/boards") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("fields", "all")
        }

        return response.body<List<TrelloBoard>>()
    }


    suspend fun getLists(boardId: String): List<Map<String, Any>> {
        val response: HttpResponse = client.get("$baseUrl/boards/$boardId/lists") {
            parameter("key", apiKey)
            parameter("token", token)
        }

        return response.body<List<Map<String, Any>>>()
    }

    suspend fun getCards(listId: String): List<Map<String, Any>> {
        val response: HttpResponse = client.get("$baseUrl/lists/$listId/cards") {
            parameter("key", apiKey)
            parameter("token", token)
        }

        return response.body<List<Map<String, Any>>>()
    }

    // TODO ajouter create/update/delete ici ensuite
}
