package com.TrustFlow_Backend_Auth.domain.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.utils.PasswordHasher

class LoginUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, password: String): User? {
        val user = userRepository.findUserByUsername(username)
        if (user != null && PasswordHasher.verify(password, user.password)) {
            return user
        }
        return null
    }
}