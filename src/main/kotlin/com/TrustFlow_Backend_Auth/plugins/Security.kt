package com.TrustFlow_Backend_Auth.plugins

import com.TrustFlow_Backend_Auth.sessions.UserSession
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*


fun Application.configureSecurity() {
    install(Sessions) {
        cookie<UserSession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
    routing {
    }
}
