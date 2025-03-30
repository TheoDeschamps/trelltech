package com.trelltech.models

import kotlinx.serialization.Serializable

@Serializable
data class TrelloBoard(
    val id: String,
    val name: String,
    val url: String,
    val shortUrl: String,
    val closed: Boolean,
    val pinned: Boolean,
    val starred: Boolean,
    val idOrganization: String? = null,
    val idMemberCreator: String? = null,
    val desc: String? = null,
    val prefs: BoardPrefs? = null
)

@Serializable
data class BoardPrefs(
    val permissionLevel: String? = null,
    val voting: String? = null,
    val comments: String? = null,
    val invitations: String? = null,
    val selfJoin: Boolean? = null,
    val cardCovers: Boolean? = null,
    val background: String? = null
)
