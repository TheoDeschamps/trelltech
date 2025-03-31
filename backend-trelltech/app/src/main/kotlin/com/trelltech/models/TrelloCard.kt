package com.trelltech.models

import kotlinx.serialization.Serializable

@Serializable
data class TrelloCard(
    val id: String,
    val name: String,
    val idList: String,
    var idMembers: List<String> = emptyList(),
    val desc: String? = null,
    val closed: Boolean = false
)
