package com.TrustFlow_Backend_Auth.models
import kotlinx.serialization.Serializable

//Register
@Serializable
data class UserRegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

//Login
@Serializable
data class UserLoginRequest(
    val username: String,
    val password: String
)

