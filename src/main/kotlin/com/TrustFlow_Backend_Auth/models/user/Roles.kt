package com.TrustFlow_Backend_Auth.models.user

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    USER,
    ADMIN
}