package com.TrustFlow_Backend_Auth.domain.repositories

import com.TrustFlow_Backend_Auth.models.user.User

interface UserRepository {
    suspend fun addUser(user: User): User?
    suspend fun findUserByUsername(username: String): User?
    suspend fun updateUser(id: Int, updatedUser: User): Boolean
    suspend fun deleteUser(id: Int): Boolean
    suspend fun findUserById(id: Int): User?
    suspend fun getAllUsers(): List<User>
}