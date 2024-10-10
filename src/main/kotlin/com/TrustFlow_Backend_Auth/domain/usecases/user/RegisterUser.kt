package com.TrustFlow_Backend_Auth.domain.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.models.Role
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.models.UserRegisterRequest
import com.TrustFlow_Backend_Auth.utils.PasswordHasher

class RegisterUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(userRequest: UserRegisterRequest): User? {
        val hashedPassword = PasswordHasher.hash(userRequest.password)

        val newUser = User(
            username = userRequest.username,
            password = hashedPassword,
            email = userRequest.email,
            role = Role.USER,
        )

        return userRepository.addUser(newUser)
    }
}
