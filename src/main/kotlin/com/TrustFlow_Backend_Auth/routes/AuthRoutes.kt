package com.TrustFlow_Backend_Auth.routes

import com.TrustFlow_Backend_Auth.data.repositories.UserRepositoryImpl
import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.DeleteUser
import com.TrustFlow_Backend_Auth.domain.usecases.LoginUser
import com.TrustFlow_Backend_Auth.domain.usecases.RegisterUser
import com.TrustFlow_Backend_Auth.domain.usecases.UpdateUser
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.models.UserRegisterRequest
import com.TrustFlow_Backend_Auth.models.UserResponse
import com.TrustFlow_Backend_Auth.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.authRoutes() {
    // Initialize repository and use cases
    val userRepository: UserRepository = UserRepositoryImpl()
    val registerUser = RegisterUser(userRepository)
    val loginUser = LoginUser(userRepository)
    val updateUser = UpdateUser(userRepository)
    val deleteUser = DeleteUser(userRepository)

    post("/register") {
        val user = call.receive<UserRegisterRequest>()
        val addedUser = registerUser(user)
        if (addedUser != null) {
            val response = UserResponse(status = "User registered", user = addedUser)
            call.respond(response)
        } else {
            val response = UserResponse(status = "Registration failed", user = null)
            call.respond(response)
        }
    }

    post("/login") {
        val credentials = call.receive<User>()
        val user = loginUser(credentials.username, credentials.password)
        if (user != null) {
            call.sessions.set(UserSession(user.id!!))
            call.respond(mapOf("status" to "Logged in", "user" to user))
        } else {
            call.respond(mapOf("status" to "Invalid credentials"))
        }
    }

    post("/logout") {
        call.sessions.clear<UserSession>()
        call.respond(mapOf("status" to "Logged out"))
    }

    put("/update") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@put
        }
        val updatedUser = call.receive<User>()
        val success = updateUser(session.userId, updatedUser)
        if (success) {
            call.respond(mapOf("status" to "User updated"))
        } else {
            call.respond(mapOf("status" to "Update failed"))
        }
    }

    delete("/delete") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@delete
        }
        val success = deleteUser(session.userId)
        if (success) {
            call.sessions.clear<UserSession>()
            call.respond(mapOf("status" to "User deleted"))
        } else {
            call.respond(mapOf("status" to "Deletion failed"))
        }
    }
}