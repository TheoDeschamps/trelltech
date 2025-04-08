package com.trelltech.frontend.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class CardsAPI {
    private val client = HttpClient(CIO)
    // GET
//    suspend fun getById(id: String) {}
    
    //POST
    suspend fun create(name: String, listId: String, desc: String, userId: String): HttpResponse {
        val response: HttpResponse = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = " 127.0.0.1:8080"
                appendPathSegments("cards")
                parameters.append("name", name)
                parameters.append("desc", desc)
                parameters.append("listId", listId)
                parameters.append("userId", userId)
            }
        }
        return response
    }
    
    suspend fun assign( cardId: String, memberId: String, userId: String, params: Map<String, String>): HttpResponse {
        val response: HttpResponse = client.post {
            url { 
                protocol = URLProtocol.HTTP
                host = " 127.0.0.1:8080"
                appendPathSegments("cards", cardId, "assign")
                parameters.append("memberId", memberId)
                parameters.append("userId", userId)

            }
        }
        return response
    }

//    suspend fun create(name: String, idList: String, params: Class<S>) {
////        desc: String, pos: String, due: String, start: String, dueComplete: Boolean, idMembers: Array<String>,  idLabels: Array<String>,
////        urlSource: String, mimeType: String, idCardSource: String, keepFromSource: String, address: String, locationName: String,
////        coordinates: String
//    }
    //UPDATE
    suspend fun update(name: String, listId: String, desc: String, userId: String): HttpResponse {
        val response: HttpResponse = client.patch {
            url {
                protocol = URLProtocol.HTTP
                host = " 127.0.0.1:8080"
                appendPathSegments("cards")
                parameters.append("name", name)
                parameters.append("desc", desc)
                parameters.append("listId", listId)
                parameters.append("userId", userId)
            }
        }
        return response
    }

//    suspend fun update (id: String, name: String, desc: String, closed: Boolean, IdMembers: Array<String>, IdAttachmentCover: String, idList: String,
//                idLabels: Array<String>, pos: String, due: String, start: String, dueComplete: Boolean, subscribed: Boolean,
//                address: String, locationName: String, coordinates: String) {
//
//    }
//    suspend fun updateCover (id: String, color: String, brightness: String, url: String, idAttachment: String, size: String ) {}
    
    //DELETE
    suspend fun delete (id: String): HttpResponse {
        val response: HttpResponse = client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = " 127.0.0.1:8080"
                appendPathSegments("cards", id)
            }
        }
        return response
    }
}