package com.trelltech

import com.trelltech.config.TrelloClient
import com.trelltech.models.TrelloCard
import com.trelltech.services.TrelloService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrelloServiceCardSequenceTest {

    private val mockedClient = mockk<TrelloClient>()
    private lateinit var service: TrelloService
    private val testUserId = "test-user"
    private val testToken = "abc123"

    @BeforeEach
    fun setup() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.drop(com.trelltech.models.Tokens)
            SchemaUtils.create(com.trelltech.models.Tokens)
            com.trelltech.models.Tokens.insert {
                it[userId] = testUserId
                it[token] = testToken
            }
        }
        service = TrelloService { mockedClient }
    }

    @Test
    fun cardCrudSequenceTest() = runBlocking {
        val listId = "list123"

        ////// Create //////
        val cardName = "Test Card"
        val cardDesc = "Initial description"
        val createdCard = TrelloCard("card001", cardName, listId, desc = cardDesc)
        coEvery { mockedClient.createCard(listId, cardName, cardDesc) } returns createdCard

        val resultCreate = service.createCard(listId, cardName, cardDesc, testUserId)
        assertEquals(createdCard, resultCreate)

        ////// Update //////
        val updatedName = "Updated Card"
        val updatedDesc = "Updated description"
        val updatedCard = TrelloCard("card001", updatedName, listId, desc = updatedDesc)
        coEvery { mockedClient.updateCard("card001", updatedName, updatedDesc) } returns updatedCard

        val resultUpdate = service.updateCard("card001", updatedName, updatedDesc, testUserId)
        assertEquals(updatedCard, resultUpdate)

        ////// Assign //////
        val memberId = "member001"
        coEvery { mockedClient.assignMemberToCard("card001", memberId) } returns Unit
        service.assignMember("card001", memberId, testUserId)

        ////// Delete //////
        coEvery { mockedClient.deleteCard("card001") } returns Unit
        service.deleteCard("card001", testUserId)

        coVerifyOrder {
            mockedClient.createCard(listId, cardName, cardDesc)
            mockedClient.updateCard("card001", updatedName, updatedDesc)
            mockedClient.assignMemberToCard("card001", memberId)
            mockedClient.deleteCard("card001")
        }
    }
}
