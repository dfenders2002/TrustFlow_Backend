package com.TrustFlow_Backend_Auth.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val status: String,
    val user: User?
)

@Serializable
data class UsersResponse(
    val status: String,
    val users: List<User>
)
