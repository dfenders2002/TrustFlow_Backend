package com.TrustFlow_Backend_Auth.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.user.LoginUser
import com.TrustFlow_Backend_Auth.models.user.Role
import com.TrustFlow_Backend_Auth.models.user.User
import com.TrustFlow_Backend_Auth.utils.PasswordHasher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LoginUserTest {

    private val userRepository = mockk<UserRepository>()
    private val loginUser = LoginUser(userRepository)

    @Test
    fun `should login user with correct credentials`() = runBlocking {
        val username = "testuser"
        val password = "password123"
        val hashedPassword = PasswordHasher.hash(password)
        val user = User(id = 1, username = username, password = hashedPassword, email = "test@example.com", role = Role.USER)

        coEvery { userRepository.findUserByUsername(username) } returns user

        val result = loginUser(username, password)

        assertNotNull(result)
        assertEquals(user, result)
        coVerify(exactly = 1) { userRepository.findUserByUsername(username) }
    }

    @Test
    fun `should return null with incorrect password`() = runBlocking {
        val username = "testuser"
        val password = "password123"
        val wrongPassword = "wrongpassword"
        val hashedPassword = PasswordHasher.hash(password)
        val user = User(id = 1, username = username, password = hashedPassword, email = "test@example.com", role = Role.USER)

        coEvery { userRepository.findUserByUsername(username) } returns user

        val result = loginUser(username, wrongPassword)

        assertNull(result)
        coVerify(exactly = 1) { userRepository.findUserByUsername(username) }
    }

    @Test
    fun `should return null when user not found`() = runBlocking {
        val username = "nonexistentuser"
        val password = "password123"

        coEvery { userRepository.findUserByUsername(username) } returns null

        val result = loginUser(username, password)

        assertNull(result)
        coVerify(exactly = 1) { userRepository.findUserByUsername(username) }
    }
}
