package com.TrustFlow_Backend_Auth.usecases

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.RegisterUser
import com.TrustFlow_Backend_Auth.models.Role
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.utils.PasswordHasher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RegisterUserTest {

    private val userRepository = mockk<UserRepository>()
    private val registerUser = RegisterUser(userRepository)

    @Test
    fun `should register a new user`() = runBlocking {
        // Arrange
        val user = User(username = "testuser", password = "password123", email = "test@example.com")
        val hashedPassword = PasswordHasher.hash(user.password)
        val userWithHashedPassword = user.copy(password = hashedPassword)
        val savedUser = userWithHashedPassword.copy(id = 1)

        coEvery { userRepository.addUser(any()) } returns savedUser

        // Act
        val result = registerUser(user)

        // Assert
        assertNotNull(result)
        assertEquals(savedUser, result)

        // Verify that addUser was called with a User object that has the correct username, email, role,
        // and that the password is hashed (i.e., different from the plain password).
        coVerify(exactly = 1) {
            userRepository.addUser(match {
                it.username == "testuser" &&
                        it.email == "test@example.com" &&
                        it.role == Role.USER &&
                        it.password != "password123" &&
                        PasswordHasher.verify("password123", it.password)
            })
        }
    }

    @Test
    fun `should return null when registration fails`() = runBlocking {
        // Arrange
        val user = User(username = "testuser", password = "password123", email = "test@example.com")
        val hashedPassword = PasswordHasher.hash(user.password)
        val userWithHashedPassword = user.copy(password = hashedPassword)

        coEvery { userRepository.addUser(any()) } returns null

        // Act
        val result = registerUser(user)

        // Assert
        assertNull(result)
        coVerify(exactly = 1) {
            userRepository.addUser(match {
                it.username == "testuser" &&
                        it.email == "test@example.com" &&
                        it.role == Role.USER &&
                        it.password != "password123" &&
                        PasswordHasher.verify("password123", it.password)
            })
        }
    }
}