package com.TrustFlow_Backend_Auth.routes

import com.TrustFlow_Backend_Auth.data.repositories.UserRepositoryImpl
import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.DeleteUser
import com.TrustFlow_Backend_Auth.domain.usecases.LoginUser
import com.TrustFlow_Backend_Auth.domain.usecases.RegisterUser
import com.TrustFlow_Backend_Auth.domain.usecases.UpdateUser
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.models.UserLoginRequest
import com.TrustFlow_Backend_Auth.models.UserRegisterRequest
import com.TrustFlow_Backend_Auth.models.UserResponse
import com.TrustFlow_Backend_Auth.sessions.UserSession
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
            call.sessions.set(UserSession(addedUser.id!!))
            val response = UserResponse(status = "User registered", user = addedUser)
            call.respond(response)
        } else {
            val response = UserResponse(status = "Registration failed", user = null)
            call.respond(response)
        }
    }

    post("/login") {
        val credentials = call.receive<UserLoginRequest>()
        val user = loginUser(credentials.username, credentials.password)
        if (user != null) {
            call.sessions.set(UserSession(user.id!!))
            val response = UserResponse(status = "Logged in", user = user)
            call.respond(response)
        } else {
            val response = UserResponse(status = "Invalid credentials", user = null)
            call.respond(response)
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
        if (updatedUser.id == null || updatedUser.id != session.userId) {
            call.respond(mapOf("status" to "Invalid user ID"))
            return@put
        }
        val success = updateUser(updatedUser.id, updatedUser)
        if (success) {
            val user = userRepository.findUserById(updatedUser.id)
            val response = UserResponse(status = "User updated", user = user)
            call.respond(response)
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

    get("/user") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(mapOf("status" to "Unauthorized"))
            return@get
        }
        val user = userRepository.findUserById(session.userId)
        if (user != null) {
            call.respond(UserResponse(status = "success", user = user))
        } else {
            call.respond(mapOf("status" to "User not found"))
        }
    }

}