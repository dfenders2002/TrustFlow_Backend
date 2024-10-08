package com.TrustFlow_Backend_Auth.models.task

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val description: String,
    val priority: Priority
)