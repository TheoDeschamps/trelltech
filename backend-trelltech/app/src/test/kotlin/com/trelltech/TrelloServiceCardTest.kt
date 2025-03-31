package com.trelltech

import com.trelltech.config.TrelloClient
import com.trelltech.models.Tokens
import com.trelltech.models.TrelloCard
import com.trelltech.services.TrelloService
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrelloServiceCardTest {

    private val mockedClient = mockk<TrelloClient>()
    private lateinit var service: TrelloService
    private val testUserId = "test-user"
    private val testToken = "abc123"

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
        service = TrelloService { mockedClient }
    }

    @Test
    fun getCardsShouldReturnListOfTrelloCard() = runBlocking {
        val listId = "list123"
        val expectedCards = listOf(
            TrelloCard("1", "Card One", listId, listOf("userA"), "desc1", false),
            TrelloCard("2", "Card Two", listId, listOf("userB"), "desc2", false)
        )
        coEvery { mockedClient.getCards(listId) } returns expectedCards
        val result = service.getCards(listId, testUserId)
        assertEquals(expectedCards, result)
    }

    @Test
    fun createCardShouldReturnCreatedTrelloCard() = runBlocking {
        val listId = "list123"
        val cardName = "New Card"
        val cardDesc = "Some description"
        val expectedCard = TrelloCard("card123", cardName, listId, desc = cardDesc)
        coEvery { mockedClient.createCard(listId, cardName, cardDesc) } returns expectedCard
        val result = service.createCard(listId, cardName, cardDesc, testUserId)
        assertEquals(expectedCard, result)
    }

    @Test
    fun updateCardShouldReturnUpdatedTrelloCard() = runBlocking {
        val cardId = "cardToUpdate"
        val newName = "Updated Card"
        val newDesc = "Updated description"
        val expectedUpdatedCard = TrelloCard(cardId, newName, "list123", desc = newDesc)
        coEvery { mockedClient.updateCard(cardId, newName, newDesc) } returns expectedUpdatedCard
        val result = service.updateCard(cardId, newName, newDesc, testUserId)
        assertEquals(expectedUpdatedCard, result)
    }

    @Test
    fun deleteCardShouldCallDeleteCard() = runBlocking {
        val cardId = "cardToDelete"
        coEvery { mockedClient.deleteCard(cardId) } returns Unit
        service.deleteCard(cardId, testUserId)
        coVerify { mockedClient.deleteCard(cardId) }
    }

    @Test
    fun assignMemberShouldCallAssignMemberToCard() = runBlocking {
        val cardId = "card123"
        val memberId = "member456"
        coEvery { mockedClient.assignMemberToCard(cardId, memberId) } returns Unit
        service.assignMember(cardId, memberId, testUserId)
        coVerify { mockedClient.assignMemberToCard(cardId, memberId) }
    }
}
