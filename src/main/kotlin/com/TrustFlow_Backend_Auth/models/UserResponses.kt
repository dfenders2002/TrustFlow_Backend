package com.TrustFlow_Backend_Auth.models

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterResponse(
    val status: String,
    val user: User? = null
)

@Serializable
data class UserLoginResponse(
    val status: String,
    val user: User? = null
)
