package com.trelltech.frontend.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ListsAPI {
    private val client = HttpClient(CIO)

    suspend fun getById (id: String) {}
    suspend fun getById (id: String, fields: String) {}
    suspend fun update (id: String, name: String, closed: Boolean, idBoard: String, pos: String, subscribed: Boolean) {}
    suspend fun create (name: String,idBoard: String){}
    suspend fun create (name: String, idBoard: String, idListSource: String, pos: String) {}
    suspend fun archive (id: String, value: String = "closed") {}
    suspend fun unarchive (id: String) {}
    // fields : all or a comma-separated list of member fields Default: avatarHash,fullName,initials,username
    suspend fun addMember (id: String, fields: String) {}
}