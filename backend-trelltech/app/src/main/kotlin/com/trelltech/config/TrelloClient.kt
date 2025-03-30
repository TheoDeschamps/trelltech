package com.trelltech.config

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TrelloClient(private val token: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private val baseUrl = "https://api.trello.com/1"
    private val apiKey = System.getenv("TRELLO_API_KEY") ?: error("Missing TRELLO_API_KEY in env")

    suspend fun getBoards(): List<Map<String, Any>> {
        val response: HttpResponse = client.get("$baseUrl/members/me/boards") {
            parameter("key", apiKey)
            parameter("token", token)
        }

        return response.body()
    }

    suspend fun getLists(boardId: String): List<Map<String, Any>> {
        val response: HttpResponse = client.get("$baseUrl/boards/$boardId/lists") {
            parameter("key", apiKey)
            parameter("token", token)
        }

        return response.body()
    }

    suspend fun getCards(listId: String): List<Map<String, Any>> {
        val response: HttpResponse = client.get("$baseUrl/lists/$listId/cards") {
            parameter("key", apiKey)
            parameter("token", token)
        }

        return response.body()
    }

    // TODO ajouter create/update/delete ici ensuite
}
