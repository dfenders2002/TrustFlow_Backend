package com.TrustFlow_Backend_Auth.routes

import com.TrustFlow_Backend_Auth.dao.UserDao
import com.TrustFlow_Backend_Auth.models.User
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

data class UserSession(val userId: Int)

fun Route.authRoutes() {
    val userDao = UserDao()

    post("/register") {
        val user = call.receive<User>()
        val addedUser = userDao.addUser(user)
        if (addedUser != null) {
            call.respond(mapOf("status" to "User registered", "user" to addedUser))
        } else {
            call.respond(mapOf("status" to "Registration failed"))
        }
    }

    post("/login") {
        val credentials = call.receive<User>()
        val user = userDao.findUserByUsername(credentials.username)
        if (user != null && user.password == credentials.password) {
            call.sessions.set(UserSession(user.id!!))
            val userDTO = User(id = user.id, username = user.username, password = user.password, email = user.email)
            call.respond(mapOf("status" to "Logged in", "user" to userDTO))
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
        val success = userDao.updateUser(session.userId, updatedUser)
        if (success) {
            call.respond(mapOf("status" to "User updated"))
        } else {
            call.respond(mapOf("status" to "Update failed"))
        }
    }

//    delete("/delete") {
//        val session = call.sessions.get<UserSession>()
//        if (session == null) {
//            call.respond(mapOf("status" to "Unauthorized"))
//            return@delete
//        }
//        val success = userDao.deleteUser(session.userId)
//        if (success) {
//            call.sessions.clear<UserSession>()
//            call.respond(mapOf("status" to "User deleted"))
//        } else {
//            call.respond(mapOf("status" to "Deletion failed"))
//        }
//    }
}