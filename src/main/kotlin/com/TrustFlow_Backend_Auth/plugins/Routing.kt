package com.TrustFlow_Backend_Auth.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.TrustFlow_Backend_Auth.routes.authRoutes

fun Application.configureRouting() {
    routing {
        authRoutes()
    }
}
