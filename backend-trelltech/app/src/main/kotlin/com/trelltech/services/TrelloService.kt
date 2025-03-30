package com.trelltech.services

import com.trelltech.config.TrelloClient
import com.trelltech.models.Tokens
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class TrelloService {

    private fun getTokenForUser(userId: String): String? {
        return transaction {
            Tokens
                .select { Tokens.userId eq userId }
                .mapNotNull { it[Tokens.token] }
                .singleOrNull()
        }
    }

    suspend fun getBoards(userId: String): List<Map<String, Any>> {
        val token = getTokenForUser(userId) ?: throw IllegalStateException("Token not found for user $userId")
        val client = TrelloClient(token)
        return client.getBoards()
    }

    suspend fun getLists(userId: String, boardId: String): List<Map<String, Any>> {
        val token = getTokenForUser(userId) ?: throw IllegalStateException("Token not found for user $userId")
        val client = TrelloClient(token)
        return client.getLists(boardId)
    }

    suspend fun getCards(userId: String, listId: String): List<Map<String, Any>> {
        val token = getTokenForUser(userId) ?: throw IllegalStateException("Token not found for user $userId")
        val client = TrelloClient(token)
        return client.getCards(listId)
    }
}
