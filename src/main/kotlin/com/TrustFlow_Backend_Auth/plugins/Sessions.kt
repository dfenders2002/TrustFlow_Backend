package com.TrustFlow_Backend_Auth.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSessions() {
    install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60 * 60 * 24 // 1 day
            cookie.httpOnly = true
            // For HTTPS:
            // cookie.secure = true
        }
    }
}