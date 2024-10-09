package com.TrustFlow_Backend_Auth.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val status: String,
    val user: User?
)
