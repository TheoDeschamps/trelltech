package com.trelltech.frontend.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.call.body


class OrganizationsAPI {
    val client = HttpClient(CIO)

    suspend fun getActions(id: String) {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("organizations", id, "actions")
            }
        }
        return response.body()
    }
    suspend fun get(id: String): HttpResponse {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("organizations", id)
            }
        }
        return response
    }
    suspend fun getBoards(id: String): HttpResponse {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("organizations", id, "boards")
            }
        }
        return response
    }
    suspend fun create(displayName: String, desc: String, website: String) {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("organizations")
                parameters.append("displayName", displayName)
                parameters.append("desc", desc)
                parameters.append("website", website)
            }
        }
        return response.body()
    }
    suspend fun update(id: String, name: String, displayName: String, desc: String, website: String) {
        val response: HttpResponse = client.put {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("organizations", id)
                parameters.append("name", name)
                parameters.append("displayName", displayName)
                parameters.append("desc", desc)
                parameters.append("website", website)
            }
        }
    }
    suspend fun delete(id: String) {
        val response: HttpResponse = client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = " http://127.0.0.1:8080"
                appendPathSegments("organizations", id)
            }
        }
    }
}