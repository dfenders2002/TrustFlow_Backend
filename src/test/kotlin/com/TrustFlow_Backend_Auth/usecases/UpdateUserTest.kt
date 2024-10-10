package com.TrustFlow_Backend_Auth.usecases

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.user.UpdateUser
import com.TrustFlow_Backend_Auth.models.Role
import com.TrustFlow_Backend_Auth.models.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UpdateUserTest {

    private val userRepository = mockk<UserRepository>()
    private val updateUser = UpdateUser(userRepository)

    @Test
    fun `should update user successfully`() = runBlocking {
        val userId = 1
        val updatedUser = User(id = userId, username = "updateduser", password = "newpassword", email = "updated@example.com", role = Role.USER)

        coEvery { userRepository.updateUser(userId, updatedUser) } returns true

        val result = updateUser(userId, updatedUser)

        assertTrue(result)
        coVerify { userRepository.updateUser(userId, updatedUser) }
    }

    @Test
    fun `should return false when update fails`() = runBlocking {
        val userId = 1
        val updatedUser = User(id = userId, username = "updateduser", password = "newpassword", email = "updated@example.com", role = Role.USER)

        coEvery { userRepository.updateUser(userId, updatedUser) } returns false

        val result = updateUser(userId, updatedUser)

        assertFalse(result)
        coVerify { userRepository.updateUser(userId, updatedUser) }
    }
}
