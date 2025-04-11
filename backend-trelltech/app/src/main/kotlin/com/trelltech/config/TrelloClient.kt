package com.trelltech.config

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv
import com.trelltech.models.TrelloBoard
import com.trelltech.models.TrelloCard
import com.trelltech.models.TrelloList
import com.trelltech.models.TrelloMember
import com.trelltech.models.TrelloWorkspace
import io.ktor.client.call.body
import io.ktor.http.isSuccess

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

    //////// BOARDS CRUD////////

    suspend fun getBoards(): List<TrelloBoard> {
        val response: HttpResponse = client.get("$baseUrl/members/me/boards") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("fields", "all")
        }

        return response.body<List<TrelloBoard>>()
    }

    suspend fun createBoard(name: String, desc: String? = null): TrelloBoard {
        val response: HttpResponse = client.post("$baseUrl/boards") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("name", name)
            if (desc != null) parameter("desc", desc)
        }

        if (!response.status.isSuccess()) {
            val errorText = response.bodyAsText()
            throw RuntimeException("Erreur Trello: ${response.status} - $errorText")
        }
        return response.body()
    }

    suspend fun deleteBoard(boardId: String) {
        client.delete("$baseUrl/boards/$boardId") {
            parameter("key", apiKey)
            parameter("token", token)
        }
    }

    suspend fun updateBoard(boardId: String, name: String, desc: String?): TrelloBoard {
        val response: HttpResponse = client.put("$baseUrl/boards/$boardId") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("name", name)
            if (desc != null) parameter("desc", desc)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Erreur Trello: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body()
    }

    //////// LISTS CRUD////////

    suspend fun getLists(boardId: String): List<TrelloList> {
        val response: HttpResponse = client.get("$baseUrl/boards/$boardId/lists") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("fields", "all")
        }

        return response.body<List<TrelloList>>()
    }

    suspend fun createList(boardId: String, name: String): TrelloList {
        val response: HttpResponse = client.post("$baseUrl/lists") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("name", name)
            parameter("idBoard", boardId)
        }

        if (!response.status.isSuccess()) {
            val errorText = response.bodyAsText()
            throw RuntimeException("Erreur Trello: ${response.status} - $errorText")
        }
        return response.body()
    }

    suspend fun updateList(listId: String, name: String): TrelloList {
        val response: HttpResponse = client.put("$baseUrl/lists/$listId") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("name", name)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Erreur Trello: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body()
    }

    suspend fun deleteList(listId: String) {
        client.delete("$baseUrl/lists/$listId") {
            parameter("key", apiKey)
            parameter("token", token)
        }
    }

    //////// CARDS CRUD////////

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

        if (!response.status.isSuccess()) {
            val errorText = response.bodyAsText()
            throw RuntimeException("Erreur Trello: ${response.status} - $errorText")
        }
        return response.body()
    }


    suspend fun deleteCard(cardId: String) {
        client.delete("$baseUrl/cards/$cardId") {
            parameter("key", apiKey)
            parameter("token", token)
        }
    }

    suspend fun assignMemberToCard(cardId: String, memberId: String) {
        val response: HttpResponse = client.post("$baseUrl/cards/$cardId/idMembers") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("value", memberId)
        }

        if (!response.status.isSuccess()) {
            val errorText = response.bodyAsText()
            throw RuntimeException("Erreur Trello assignation : ${response.status} - $errorText")
        }
    }

    suspend fun updateCard(cardId: String, name: String?, desc: String?): TrelloCard {
        val response: HttpResponse = client.put("$baseUrl/cards/$cardId") {
            parameter("key", apiKey)
            parameter("token", token)
            if (name != null) parameter("name", name)
            if (desc != null) parameter("desc", desc)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Erreur Trello: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body()
    }


    //////// WORKSPACES R////////
    // TODO CUD pour les workspaces

    suspend fun getWorkspaces(): List<TrelloWorkspace> {
        val response: HttpResponse = client.get("$baseUrl/members/me/organizations") {
            parameter("key", apiKey)
            parameter("token", token)
        }
        return response.body()
    }
    //////// MEMBERS R////////
    suspend fun getMember(memberId: String): TrelloMember {
        val response: HttpResponse = client.get("$baseUrl/members/$memberId") {
            parameter("key", apiKey)
            parameter("token", token)
            parameter("fields", "username,fullName")
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Erreur Trello getMember: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body()
    }

}


