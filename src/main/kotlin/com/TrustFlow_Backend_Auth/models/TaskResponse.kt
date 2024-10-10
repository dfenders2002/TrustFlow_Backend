package com.TrustFlow_Backend_Auth.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val status: String,
    val task: Task? = null
)

@Serializable
data class TasksResponse(
    val status: String,
    val tasks: List<Task>
)