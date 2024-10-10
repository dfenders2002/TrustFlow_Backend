package com.TrustFlow_Backend_Auth.usecases.user

import com.TrustFlow_Backend_Auth.domain.repositories.UserRepository
import com.TrustFlow_Backend_Auth.domain.usecases.user.DeleteUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DeleteUserTest {

    private val userRepository = mockk<UserRepository>()
    private val deleteUser = DeleteUser(userRepository)

    @Test
    fun `should delete user successfully`() = runBlocking {
        val userId = 1

        coEvery { userRepository.deleteUser(userId) } returns true

        val result = deleteUser(userId)

        assertTrue(result)
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }

    @Test
    fun `should return false when deletion fails`() = runBlocking {
        val userId = 1

        coEvery { userRepository.deleteUser(userId) } returns false

        val result = deleteUser(userId)

        assertFalse(result)
        coVerify(exactly = 1) { userRepository.deleteUser(userId) }
    }
}
