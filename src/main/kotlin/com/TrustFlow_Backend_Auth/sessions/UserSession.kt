package com.TrustFlow_Backend_Auth.sessions

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val userId: Int)
