package com.TrustFlow_Backend_Auth.models

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    USER,
    ADMIN
}