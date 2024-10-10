package com.TrustFlow_Backend_Auth.domain.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository

class DeleteUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return userRepository.deleteUser(id)
    }
}