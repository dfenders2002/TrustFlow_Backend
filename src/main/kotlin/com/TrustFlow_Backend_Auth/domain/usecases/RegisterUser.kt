package com.TrustFlow_Backend_Auth.domain.usecases

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.utils.PasswordHasher

class RegisterUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User): User? {
        // Hash the password
        val hashedPassword = PasswordHasher.hash(user.password)
        val newUser = user.copy(password = hashedPassword)
        // Add the user to the repository
        return userRepository.addUser(newUser)
    }
}