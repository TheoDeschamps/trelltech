package com.trelltech.models

import kotlinx.serialization.Serializable

@Serializable
data class TrelloMember(
    val id: String,
    val username: String,
    val fullName: String
)
