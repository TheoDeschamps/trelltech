package com.trelltech.models

import org.jetbrains.exposed.sql.Table

object Tokens : Table() {
    val id = integer("id").autoIncrement()
    val userId = varchar("user_id", 255)
    val token = varchar("token", 512)

    override val primaryKey = PrimaryKey(id)
}
