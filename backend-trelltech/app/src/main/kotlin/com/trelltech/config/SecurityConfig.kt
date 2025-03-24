package com.trelltech.config

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.net.URLEncoder
import io.github.cdimascio.dotenv.dotenv

object SecurityConfig {

    private val dotenv = dotenv()

    private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    private val CLIENT_ID: String by lazy { dotenv["TRELLO_CLIENT_ID"] ?: "your-client-id" }
    private val CLIENT_SECRET: String by lazy { dotenv["TRELLO_CLIENT_SECRET"] ?: "your-client-secret" }
    private val REDIRECT_URI: String = "trelltech://callback"

    private const val AUTHORIZE_URL = "https://trello.com/1/authorize"
    private const val ACCESS_TOKEN_URL = "https://trello.com/1/oauth/token"

    private val httpClient = HttpClient()

    fun getAuthUrl(): String {
        val encodedRedirectUri = URLEncoder.encode(REDIRECT_URI, "UTF-8")
        val authUrl = "$AUTHORIZE_URL?response_type=token&key=$CLIENT_ID&return_url=$encodedRedirectUri&callback_method=fragment"

        println("Generated Trello Auth URL: $authUrl")
        return authUrl
    }

    suspend fun getAccessToken(token: String): TrelloTokenResponse? {
        val response: HttpResponse = httpClient.get(ACCESS_TOKEN_URL) {
            url {
                parameters.append("key", CLIENT_ID)
                parameters.append("token", token)
            }
        }

        return if (response.status == HttpStatusCode.OK) {
            val responseBody = response.body<String>()
            Json.decodeFromString<TrelloTokenResponse>(responseBody)
        } else {
            logger.error("Failed to retrieve Trello token: ${response.status}")
            null
        }
    }
}

@Serializable
data class TrelloTokenResponse(
    val token: String
)
