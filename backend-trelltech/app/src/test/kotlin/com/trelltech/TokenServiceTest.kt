package com.trelltech

import com.trelltech.models.Tokens
import com.trelltech.services.TokenService
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TokenServiceTest {

    private val tokenService = TokenService()

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction {
            create(Tokens)
        }
    }

    @Test
    fun `should store and retrieve token`() {
        val userId = "testUser"
        val token = "abc123"

        tokenService.saveToken(userId, token)
        val retrievedToken = tokenService.getToken(userId)

        assertEquals(token, retrievedToken)
    }

    @Test
    fun `should update token if user already exists`() {
        val userId = "testUser"
        val initialToken = "initToken"
        val updatedToken = "newToken"

        tokenService.saveToken(userId, initialToken)
        tokenService.saveToken(userId, updatedToken)
        val retrieved = tokenService.getToken(userId)

        assertEquals(updatedToken, retrieved)
    }
}
