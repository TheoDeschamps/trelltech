package com.trelltech

import com.trelltech.models.Tokens
import com.trelltech.models.TrelloBoard
import com.trelltech.services.TrelloService
import io.mockk.coEvery
import io.mockk.mockkConstructor
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrelloServiceBoardTest {

    private lateinit var service: TrelloService

    @BeforeEach
    fun setup() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.drop(Tokens)
            SchemaUtils.create(Tokens)
            Tokens.insert {
                it[userId] = "testUser"
                it[token] = "mockToken"
            }
        }
        service = TrelloService()
        mockkConstructor(com.trelltech.config.TrelloClient::class)
    }

    @Test
    fun `getBoards should return list of TrelloBoard`() = runBlocking {
        val expectedBoards = listOf(
            TrelloBoard(
                id = "1",
                name = "Board 1",
                url = "https://trello.com/b/1",
                shortUrl = "short1",
                closed = false,
                pinned = false,
                starred = false
            )
        )

        coEvery { anyConstructed<com.trelltech.config.TrelloClient>().getBoards() } returns expectedBoards

        val result = service.getBoards("testUser")

        assertEquals(expectedBoards, result)
    }
}
