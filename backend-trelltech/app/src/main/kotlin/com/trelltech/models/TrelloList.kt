package com.trelltech.models

import kotlinx.serialization.Serializable

@Serializable
data class TrelloList(
    val id: String,
    val name: String,
    val idBoard: String,
    val closed: Boolean
)
