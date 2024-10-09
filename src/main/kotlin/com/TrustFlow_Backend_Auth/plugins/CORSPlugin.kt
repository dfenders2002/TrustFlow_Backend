package com.TrustFlow_Backend_Auth.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    install(CORS) {
        allowCredentials = true
        allowHost("localhost:3000", schemes = listOf("http"))
        allowHost("127.0.0.1:3000", schemes = listOf("http"))
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("Set-Cookie")
        exposeHeader("Set-Cookie")
    }
}
