package com.trelltech

import com.trelltech.config.TrelloClient
import com.trelltech.models.Tokens
import com.trelltech.models.TrelloBoard
import com.trelltech.services.TrelloService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrelloServiceBoardTest {

    private val mockedClient = mockk<TrelloClient>()
    private lateinit var service: TrelloService
    private val testUserId = "testUser"
    private val testToken = "mockToken"

    @BeforeEach
    fun setup() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.drop(Tokens)
            SchemaUtils.create(Tokens)
            Tokens.insert {
                it[userId] = testUserId
                it[token] = testToken
            }
        }
        service = TrelloService(clientFactory = { mockedClient })
    }

    @Test
    fun getBoardsShouldReturnListOfTrelloBoard() = runBlocking {
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
        coEvery { mockedClient.getBoards() } returns expectedBoards
        val result = service.getBoards(testUserId)
        assertEquals(expectedBoards, result)
    }
}
