package com.trelltech.config

import com.trelltech.models.Tokens
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/trelltech"
        val dbUser = System.getenv("DB_USER") ?: "trelltech_user"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "trelltech_pass"

        Database.connect(
            url = dbUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        transaction {
            SchemaUtils.create(Tokens)
        }
    }
}
