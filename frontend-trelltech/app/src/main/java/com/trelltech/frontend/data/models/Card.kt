package com.trelltech.frontend.data.models

data class Card (
    val id : String,
    val idList : String,
    val memberList : MutableList<String>,
    val desc : String,
    val name : String,
)