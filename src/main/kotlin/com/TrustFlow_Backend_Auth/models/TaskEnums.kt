package com.TrustFlow_Backend_Auth.models

import kotlinx.serialization.Serializable

@Serializable
enum class Priority {
    HIGH,
    MEDIUM,
    LOW
}

@Serializable
enum class Status {
    COMPLETED,
    PENDING
}