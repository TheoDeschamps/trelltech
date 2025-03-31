package com.trelltech.services

import com.trelltech.models.Tokens
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TokenService {
    fun saveToken(userId: String, token: String) {
        transaction {
            if (Tokens.select { Tokens.userId eq userId }.empty()) {
                Tokens.insert {
                    it[Tokens.userId] = userId
                    it[Tokens.token] = token
                }
            } else {
                Tokens.update({ Tokens.userId eq userId }) {
                    it[Tokens.token] = token
                }
            }
        }
    }

    fun getToken(userId: String): String? {
        return transaction {
            Tokens.select { Tokens.userId eq userId }
                .map { it[Tokens.token] }
                .singleOrNull()
        }
    }
}
