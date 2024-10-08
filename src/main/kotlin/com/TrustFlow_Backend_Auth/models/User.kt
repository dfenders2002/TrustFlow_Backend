package com.TrustFlow_Backend_Auth.models

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val username: String,
    val password: String,
    val email: String
)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 255)
    val email = varchar("email", 255).uniqueIndex()
}

@Serializable
data class UserDTO(val id: Int, val username: String, val email: String)
