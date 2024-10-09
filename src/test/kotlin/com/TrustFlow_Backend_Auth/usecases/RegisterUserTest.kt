package com.TrustFlow_Backend_Auth.usecases

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.RegisterUser
import com.TrustFlow_Backend_Auth.models.Role
import com.TrustFlow_Backend_Auth.models.User
import com.TrustFlow_Backend_Auth.models.UserRegisterRequest
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
        val userRequest = UserRegisterRequest(username = "testuser", password = "password123", email = "test@example.com")
        val hashedPassword = PasswordHasher.hash(userRequest.password)
        val savedUser = User(username = userRequest.username, password = hashedPassword, email = userRequest.email, role = Role.USER, id = 1)

        coEvery { userRepository.addUser(any()) } returns savedUser

        val result = registerUser(userRequest)

        assertNotNull(result)
        assertEquals(savedUser, result)

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
        val userRequest = UserRegisterRequest(username = "testuser", password = "password123", email = "test@example.com")
        val hashedPassword = PasswordHasher.hash(userRequest.password)
        val savedUser = User(username = userRequest.username, password = hashedPassword, email = userRequest.email, role = Role.USER, id = 1)

        coEvery { userRepository.addUser(any()) } returns null

        val result = registerUser(userRequest)

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
