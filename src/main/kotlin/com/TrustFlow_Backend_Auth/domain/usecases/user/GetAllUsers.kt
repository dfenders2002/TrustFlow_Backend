package com.TrustFlow_Backend_Auth.domain.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.models.User

class GetAllUsers(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        return userRepository.getAllUsers()
    }
}