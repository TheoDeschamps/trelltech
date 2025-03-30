package com.trelltech.models

import kotlinx.serialization.Serializable

@Serializable
data class TrelloWorkspace(
    val id: String,
    val name: String,
    val displayName: String
)
