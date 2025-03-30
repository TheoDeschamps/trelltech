package com.trelltech.services

import com.trelltech.config.TrelloClient
import com.trelltech.models.Tokens
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import com.trelltech.models.TrelloBoard
import com.trelltech.models.TrelloCard
import com.trelltech.models.TrelloList

class TrelloService {

    private fun getTokenForUser(userId: String): String? {
        return transaction {
            Tokens
                .select { Tokens.userId eq userId }
                .mapNotNull { it[Tokens.token] }
                .singleOrNull()
        }
    }

    suspend fun getBoards(userId: String): List<TrelloBoard> {
        val token = getTokenForUser(userId)
            ?: throw IllegalStateException("Token not found for user $userId")

        val client = TrelloClient(token)
        return client.getBoards()
    }

    suspend fun getLists(boardId: String, userId: String): List<TrelloList> {
        val token = getTokenForUser(userId)
            ?: throw IllegalStateException("Token not found for user $userId")

        val client = TrelloClient(token)
        return client.getLists(boardId)
    }

    suspend fun getCards(listId: String, userId: String): List<TrelloCard> {
        val token = getTokenForUser(userId) ?: throw Exception("Token not found")
        return TrelloClient(token).getCards(listId)
    }

    suspend fun createCard(listId: String, name: String, desc: String?, userId: String): TrelloCard {
        val token = getTokenForUser(userId) ?: throw Exception("Token not found")
        return TrelloClient(token).createCard(listId, name, desc)
    }

    suspend fun deleteCard(cardId: String, userId: String) {
        val token = getTokenForUser(userId) ?: throw Exception("Token not found")
        TrelloClient(token).deleteCard(cardId)
    }

    suspend fun assignMember(cardId: String, memberId: String, userId: String): TrelloCard {
        val token = getTokenForUser(userId) ?: throw Exception("Token not found")
        return TrelloClient(token).assignMemberToCard(cardId, memberId)
    }

}
