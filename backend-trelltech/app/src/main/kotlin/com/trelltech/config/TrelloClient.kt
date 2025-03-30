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
import com.trelltech.models.TrelloCard
import com.trelltech.models.TrelloList
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

    suspend fun getLists(boardId: String): List<TrelloList> {
        val response: HttpResponse = client.get("$baseUrl/boards/$boardId/lists") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("fields", "all")
        }

        return response.body<List<TrelloList>>()
    }

    suspend fun getCards(listId: String): List<TrelloCard> {
        val response: HttpResponse = client.get("$baseUrl/lists/$listId/cards") {
            parameter("key", apiKey)
            parameter("token", token)
        }
        return response.body()
    }

    suspend fun createCard(listId: String, name: String, desc: String? = null): TrelloCard {
        val response: HttpResponse = client.post("$baseUrl/cards") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("idList", listId)
            parameter("name", name)
            if (desc != null) parameter("desc", desc)
        }
        return response.body()
    }

    suspend fun deleteCard(cardId: String) {
        client.delete("$baseUrl/cards/$cardId") {
            parameter("key", apiKey)
            parameter("token", token)
        }
    }

    suspend fun assignMemberToCard(cardId: String, memberId: String): TrelloCard {
        val response: HttpResponse = client.post("$baseUrl/cards/$cardId/idMembers") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("value", memberId)
        }
        return response.body()
    }


    // TODO ajouter create/update/delete ici ensuite
}
