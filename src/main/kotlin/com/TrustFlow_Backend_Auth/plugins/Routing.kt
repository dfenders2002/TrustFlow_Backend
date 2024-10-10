package com.TrustFlow_Backend_Auth.plugins

import com.TrustFlow_Backend_Auth.routes.authRoutes
import com.TrustFlow_Backend_Auth.routes.taskRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRoutes()
        taskRoutes()
    }
}
