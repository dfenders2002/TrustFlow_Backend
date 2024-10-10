package com.TrustFlow_Backend_Auth.domain.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.models.User

class UpdateUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int, updatedUser: User): Boolean {
        return userRepository.updateUser(id, updatedUser)
    }
}
